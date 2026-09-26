package com.example.ui.components

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebResourceErrorCompat
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat

/**
 * True 3D interactive brain: the real sourced Brain-Project model
 * (437 TA2-named structures, Draco-compressed) rendered offline from
 * app assets with Three.js in a WebView.
 *
 * Assets are served through [WebViewAssetLoader] over a same-origin
 * https://appassets.androidplatform.net/ URL. file:// URLs are deliberately
 * avoided: ES modules, fetch() and the Draco Web Worker are all blocked
 * or unreliable off file:// on many devices.
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
            val assetLoader = WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(ctx))
                .build()
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.allowFileAccess = true
                settings.allowContentAccess = true
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
                webViewClient = object : WebViewClientCompat() {
                    override fun shouldInterceptRequest(
                        view: WebView,
                        request: WebResourceRequest
                    ): WebResourceResponse? {
                        val url = request.url
                        if (url.host.equals(WebViewAssetLoader.DEFAULT_DOMAIN, ignoreCase = true)) {
                            val path = url.path.orEmpty()
                            if (path == "/favicon.ico" || path.endsWith("/favicon.ico")) {
                                return WebResourceResponse(
                                    "image/x-icon",
                                    null,
                                    204,
                                    "No Content",
                                    emptyMap(),
                                    java.io.ByteArrayInputStream(ByteArray(0))
                                )
                            }
                            val response = assetLoader.shouldInterceptRequest(url)
                            if (response != null) {
                                return when {
                                    path.endsWith(".glb", ignoreCase = true) -> {
                                        WebResourceResponse("model/gltf-binary", null, response.data)
                                    }
                                    path.endsWith(".wasm", ignoreCase = true) -> {
                                        WebResourceResponse("application/wasm", null, response.data)
                                    }
                                    path.endsWith(".json", ignoreCase = true) -> {
                                        WebResourceResponse("application/json", "UTF-8", response.data)
                                    }
                                    path.endsWith(".js", ignoreCase = true) -> {
                                        WebResourceResponse("application/javascript", "UTF-8", response.data)
                                    }
                                    else -> response
                                }
                            }
                            return WebResourceResponse(
                                "text/plain",
                                "UTF-8",
                                404,
                                "Not Found",
                                emptyMap(),
                                java.io.ByteArrayInputStream("Not found".toByteArray())
                            )
                        }
                        return super.shouldInterceptRequest(view, request)
                    }

                    override fun onReceivedError(
                        view: WebView,
                        request: WebResourceRequest,
                        error: WebResourceErrorCompat
                    ) {
                        super.onReceivedError(view, request, error)
                        Log.e("Brain3D", "WebView error: ${error.description} @ ${request.url}")
                    }
                }
                loadUrl("https://${WebViewAssetLoader.DEFAULT_DOMAIN}/assets/brain3d/viewer.html")
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
