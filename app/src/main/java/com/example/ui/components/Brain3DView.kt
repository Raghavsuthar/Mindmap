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

/**
 * True 3D interactive brain: the real sourced Brain-Project model
 * (437 TA2-named structures, Draco-compressed) rendered offline from
 * app assets with Three.js in a WebView.
 * - Rotate / pinch-zoom / pan, tap structure -> true atlas card
 * - Search, labels, sagittal/coronal/axial slice planes
 * - [xray] fades the cortical surface (see-through)
 * - [onStructureTap] reports (manifestId, label, region, source, category);
 *   the host maps it onto the coarse clinical model where an explicit
 *   mapping exists, otherwise the in-viewer atlas card stands alone.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Brain3DView(
    activeCircuitId: String?,
    xray: Boolean,
    onRegionTap: (String) -> Unit,
    onStructureTap: (manifestId: Int, label: String, region: String, source: String, category: String) -> Unit,
    modifier: Modifier = Modifier
) {
    // activeCircuitId is accepted for API stability; circuit emphasis is
    // intentionally NOT painted onto true anatomy (no fabricated mapping).
    remember(activeCircuitId) { activeCircuitId }

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
                // Local file:// modules, model, manifest and Draco decoder.
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
                settings.mediaPlaybackRequiresUserGesture = false
                setBackgroundColor(android.graphics.Color.parseColor("#0B0F17"))
                addJavascriptInterface(object {
                    @JavascriptInterface
                    fun onRegionTap(regionId: String) {
                        post { onRegionTap(regionId) }
                    }

                    @JavascriptInterface
                    fun onStructureTap(
                        manifestId: Int,
                        label: String,
                        region: String,
                        source: String,
                        category: String
                    ) {
                        post { onStructureTap(manifestId, label, region, source, category) }
                    }
                }, "Android")
                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        Log.d(
                            "Brain3D",
                            "${consoleMessage?.message()} -- line ${consoleMessage?.lineNumber()}"
                        )
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
                        Log.e("Brain3D", "WebView error: ${error?.description} @ ${request?.url}")
                    }
                }
                loadUrl("file:///android_asset/brain3d/viewer.html")
            }
        },
        update = { web ->
            web.evaluateJavascript(
                "if(window.setCortexOpacity) setCortexOpacity(${if (xray) "0.08" else "1.0"});",
                null
            )
        }
    )
}
