package com.example.tradinglite

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TradingLiteApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradingLiteApp() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("TradingLite • Live Chart") }) }
        ) { pad ->
            Column(Modifier.padding(pad).fillMaxSize()) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        WebView(ctx).apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            webViewClient = WebViewClient()
                            // Live chart with Binance public API - no ads, no paywall
                            loadUrl("file:///android_asset/chart/live.html")
                        }
                    }
                )
            }
        }
    }
}
