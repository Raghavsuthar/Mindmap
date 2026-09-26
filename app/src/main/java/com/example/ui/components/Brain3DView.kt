package com.example.ui.components

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import com.example.data.BrainAtlas3D

/**
 * True 3D interactive brain (Three.js + OrbitControls bundled locally in WebView assets).
 * - Rotate / pinch-zoom / pan, tap node -> one-touch detail
 * - See-through translucent shell + X-ray mode
 * - 100% offline, zero network latency, zero CORS blocking
 * - Data from evidence-based NeuroMapRepository via [BrainAtlas3D]
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

    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .testTag("brain3d_view"),
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.allowFileAccess = true
                settings.allowContentAccess = true
                @Suppress("DEPRECATION")
                settings.allowFileAccessFromFileURLs = true
                @Suppress("DEPRECATION")
                settings.allowUniversalAccessFromFileURLs = true
                settings.mediaPlaybackRequiresUserGesture = false

                setBackgroundColor(android.graphics.Color.parseColor("#0B0F17"))

                addJavascriptInterface(object {
                    @JavascriptInterface
                    fun getPayload(): String {
                        return payload
                    }

                    @JavascriptInterface
                    fun onRegionTap(regionId: String) {
                        post { onRegionTap(regionId) }
                    }
                }, "Android")

                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        Log.d("Brain3D", "${consoleMessage?.message()} -- line ${consoleMessage?.lineNumber()} (${consoleMessage?.sourceId()})")
                        return true
                    }
                }

                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        Log.e("Brain3D", "WebView error: ${error?.description}")
                    }

                    override fun onPageFinished(view: WebView, url: String) {
                        super.onPageFinished(view, url)
                        // Trigger initialization and sync state
                        view.evaluateJavascript(
                            "if(window.initBrain && window.Android && window.Android.getPayload) { initBrain(JSON.parse(window.Android.getPayload())); }",
                            null
                        )
                        view.evaluateJavascript(
                            "if(window.setActiveCircuit) { window.setActiveCircuit(${if (activeCircuitId == null) "null" else "'$activeCircuitId'"}); }",
                            null
                        )
                        view.evaluateJavascript(
                            "if(window.setXray) { window.setXray(${if (xray) "true" else "false"}); }",
                            null
                        )
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
