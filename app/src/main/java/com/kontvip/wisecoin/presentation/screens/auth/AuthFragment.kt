package com.kontvip.wisecoin.presentation.screens.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kontvip.wisecoin.databinding.FragmentAuthBinding
import com.kontvip.wisecoin.presentation.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(), JavaScriptInterface {

    companion object {
        private const val MONOBANK_URL = "https://api.monobank.ua/"
    }

    private val viewModel by viewModels<AuthViewModel>()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true

        val docsNotAvailableToast = Toast(requireContext()).apply {
            duration = Toast.LENGTH_SHORT
            setText("Документація не доступна")
        }

        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.settings?.javaScriptEnabled = true
                view?.settings?.domStorageEnabled = true
                val url = request?.url.toString()

                when {
                    url.startsWith("app://") -> handleAppScheme(url)
                    url.startsWith("market://") -> reloadWebView()
                    url == "https://api.monobank.ua/docs/" -> docsNotAvailableToast.show()
                    else -> return false
                }
                return true
            }
            //todo: somehow check if "Деактивувати" було натиснено і токен змінився

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                waitForToken(::onTokenExtracted)

                view?.loadUrl(
                    "javascript:(function() {" +
                            "var loader = document.getElementById('loader');" +
                            "if (loader && window.getComputedStyle(loader).getPropertyValue('display') !== 'none') {" +
                            "   window.androidInterface.javaScriptReloadWebView();" +
                            "}" +
                            "})();"
                )
            }

        }

        binding.webView.addJavascriptInterface(this, "androidInterface")
        binding.webView.loadUrl(MONOBANK_URL)

    }


    private fun onTokenExtracted(token: String) {
        binding.webView.evaluateJavascript(
            "javascript:(function() {" +
                    "var userDiv = document.getElementById('user');" +
                    "var button = userDiv.querySelector('.pure-button');" +
                    "if (button) {" +
                    "button.addEventListener('click', function() {" +
                    "   window.androidInterface.javaScriptWaitForToken();" +
                    "});" +
                    "   return 'success';" +
                    "}" +
                    "})();"
        ) { result ->
            if (result != "\"success\"") {
                Toast.makeText(requireContext(), "Injection failed", Toast.LENGTH_SHORT).show()
            } else {
                binding.tokenTextView.text = token
                viewModel.saveToken(token)
            }
        }
    }

    private fun waitForToken(onTokenExtracted: (String) -> Unit) {
        binding.webView.evaluateJavascript(
            "(function() { return document.querySelector('.id').textContent; })();"
        ) { value ->
            val token = value?.replace("\"", "") // Remove surrounding double quotes if present
            println("Extracted Token: $token")

            if (token.isNullOrBlank() || token == "null") {
                lifecycleScope.launch(Dispatchers.Main) {
                    delay(200L)
                    waitForToken(onTokenExtracted)
                }
            } else {
                onTokenExtracted.invoke(token)
            }
        }
    }

    private fun handleAppScheme(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.putExtra("key", "value")
        startActivity(intent)
    }

    private fun reloadWebView() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000L)
            binding.webView.evaluateJavascript(
                "javascript:(function() {" +
                        "var loader = document.getElementById('loader');" +
                        "if (loader && window.getComputedStyle(loader).getPropertyValue('display') !== 'none') {" +
                        "   window.androidInterface.javaScriptReloadWebView();" +
                        "   return 'needToReload'" +
                        "}" +
                        "})();"
            ) { result ->
                if (result == "\"needToReload\"") {
                    binding.webView.loadUrl(MONOBANK_URL)
                }
            }
        }
    }

    @JavascriptInterface
    override fun javaScriptWaitForToken() {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.tokenTextView.visibility = View.GONE
            waitForToken(::onTokenExtracted)
        }
    }

    @JavascriptInterface
    override fun javaScriptReloadWebView() {
        reloadWebView()
    }

}


interface JavaScriptInterface {

    @JavascriptInterface
    fun javaScriptWaitForToken()

    @JavascriptInterface
    fun javaScriptReloadWebView()
}