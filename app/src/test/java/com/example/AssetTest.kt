package com.example

import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import androidx.webkit.WebViewAssetLoader
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AssetTest {

  @Test
  fun testAssetsExistAndLoadable() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val assetLoader = WebViewAssetLoader.Builder()
        .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(context))
        .build()

    val urls = listOf(
        "https://appassets.androidplatform.net/assets/brain3d/viewer.html",
        "https://appassets.androidplatform.net/assets/brain3d/viewer.js",
        "https://appassets.androidplatform.net/assets/brain3d/models/brain.glb",
        "https://appassets.androidplatform.net/assets/brain3d/models/manifest.json",
        "https://appassets.androidplatform.net/assets/brain3d/functions.json",
        "https://appassets.androidplatform.net/assets/brain3d/vendor/draco/draco_decoder.wasm"
    )

    for (url in urls) {
      val res = assetLoader.shouldInterceptRequest(Uri.parse(url))
      println("URL: $url -> MimeType: ${res?.mimeType}, Stream: ${res?.data != null}")
      assertNotNull("Failed to intercept $url", res)
      assertNotNull("Null stream for $url", res?.data)
    }
  }
}
