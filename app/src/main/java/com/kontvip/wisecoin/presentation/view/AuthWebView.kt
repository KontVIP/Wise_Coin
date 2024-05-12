package com.kontvip.wisecoin.presentation.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.domain.core.MonobankToken
import com.kontvip.wisecoin.presentation.core.delegates.FragmentLifecycleScope
import com.kontvip.wisecoin.presentation.core.delegates.FragmentViewModels
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class AuthWebView : WebView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        private const val MONOBANK_URL = "https://api.monobank.ua/"
        private const val WEB_ANDROID_INTERFACE_NAME = "androidInterface"
        private const val LOADING_TIMEOUT = 6000L
        private const val JAVASCRIPT_RESTART_DELAY = 200L
    }

    private val viewModel by FragmentViewModels(AuthWebViewViewModel::class)
    private val fragmentLifecycleScope by FragmentLifecycleScope()

    init {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()


        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url = request.url.toString()
                when {
                    url.startsWith("app://") -> {
                        try {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        } catch (_: Exception) {}
                    }
                    url.startsWith("market://") -> loadUrl(MONOBANK_URL)
                    url.endsWith("/docs/") -> viewModel.displayDocsClickError()
                    else -> return false
                }
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                fragmentLifecycleScope?.launch {
                    delay(LOADING_TIMEOUT)
                    view.loadUrl(viewModel.javaScriptCodeFromRawRes(R.raw.reload_page_if_loader_is_shown))
                }
            }
        }

        addJavascriptInterface(object : JavaScriptInterface {
            @JavascriptInterface
            override fun javaScriptReloadWebView() {
                fragmentLifecycleScope?.launch {
                    delay(JAVASCRIPT_RESTART_DELAY)
                    loadUrl(MONOBANK_URL)
                }
            }
        }, WEB_ANDROID_INTERFACE_NAME)
        loadUrl(MONOBANK_URL)
    }

    fun extractToken(onTokenExtracted: (MonobankToken) -> Unit) {
        evaluateJavascript(viewModel.javaScriptCodeFromRawRes(R.raw.extract_token)) { value ->
            onTokenExtracted.invoke(viewModel.processExtractedTokenValue(value))
        }
    }

}

private interface JavaScriptInterface {
    @JavascriptInterface
    fun javaScriptReloadWebView()
}