package com.example.ui.components

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import com.example.data.BrainAtlas3D
import org.json.JSONObject

/**
 * True 3D interactive brain (Three.js + OrbitControls in a WebView).
 * - Rotate / pinch-zoom / pan, tap node -> one-touch detail
 * - See-through translucent shell + X-ray mode
 * - Data is 100% the evidence-based NeuroMapRepository via [BrainAtlas3D]
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Brain3DView(
    activeCircuitId: String?,
    xray: Boolean,
    onRegionTap: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val payload = remember { BrainAtlas3D.toJsonPayload() }
    // Escape for single-quoted JS injection: JSONObject.quote gives a
    // double-quoted JSON string; strip outer quotes then escape ' for JS.
    // Also guard against </script> breaking out of the module script.
    val escaped = remember(payload) {
        JSONObject.quote(payload).drop(1).dropLast(1)
            .replace("'", "\\'")
            .replace("</script", "<\\/script")
    }

    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .testTag("brain3d_view"),
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                setBackgroundColor(android.graphics.Color.parseColor("#0B0F17"))
                addJavascriptInterface(object {
                    @JavascriptInterface
                    fun onRegionTap(regionId: String) {
                        post { onRegionTap(regionId) }
                    }
                }, "Android")
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView, url: String) {
                        super.onPageFinished(view, url)
                        view.evaluateJavascript(
                            "window.__PENDING__ = JSON.parse('$escaped'); if(window.initBrain) initBrain(window.__PENDING__);",
                            null
                        )
                        view.evaluateJavascript(
                            "window.setActiveCircuit(${if (activeCircuitId == null) "null" else "'$activeCircuitId'"});",
                            null
                        )
                        view.evaluateJavascript("window.setXray(${if (xray) "true" else "false"});", null)
                    }
                }
                loadUrl("file:///android_asset/brain3d/brain3d.html")
            }
        },
        update = { web ->
            web.evaluateJavascript(
                "if(window.setActiveCircuit) setActiveCircuit(${if (activeCircuitId == null) "null" else "'$activeCircuitId'"});",
                null
            )
            web.evaluateJavascript("if(window.setXray) setXray(${if (xray) "true" else "false"});", null)
        }
    )
}
