package com.emamagic.login.server_name

import android.app.Activity
import android.content.Context
import android.security.KeyChain
import android.security.keystore.KeyProperties
import android.webkit.ClientCertRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import java.lang.Exception
import java.net.URLEncoder
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.cert.X509Certificate

class SSlCertificateChecker constructor(
    private val activity: Activity,
    private val authorizationEndpoint: String,
    private val tokenEndpoint: String,
    private val redirectUri: String,
    private val clientId: String,
    private val scope: String,
    private val responseType: String,
    private val launch: (String?, String, String, String, String, String, String) -> Unit
) : WebViewClient() {

    private var alias: String? = null
    private var context: Context = activity.applicationContext
    private var webView: WebView = WebView(context)

    init {
        webView.webViewClient = this
        loadUrl()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        launch.invoke(alias, authorizationEndpoint, tokenEndpoint, redirectUri, clientId, scope, responseType)
        super.onPageFinished(view, url)
    }

    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        KeyChain.choosePrivateKeyAlias(
            activity,
            { alias ->
                if (!alias.isNullOrEmpty()) {
                    this.alias = alias
                    val pk: PrivateKey? = KeyChain.getPrivateKey(context, alias)
                    val chain: Array<X509Certificate>? =
                        KeyChain.getCertificateChain(context, alias)

                    val data = "limonad".toByteArray(charset("ASCII"))
                    val sig: Signature = Signature.getInstance("SHA1withRSA")
                    sig.initSign(pk)
                    sig.update(data)
                    val signed: ByteArray = sig.sign()

                    val pubk: PublicKey = chain!![0].publicKey
                    sig.initVerify(pubk)
                    sig.update(data)
                    val valid: Boolean = sig.verify(signed)

                    if (valid) {
                        request?.proceed(pk, chain)
                    }
                } else {
                    super.onReceivedClientCertRequest(view, request)
                }
            },
            arrayOf(
                KeyProperties.KEY_ALGORITHM_RSA,
                "DSA"
            ),
            null,
            null,
            -1,
            ""
        )
    }


    private fun loadUrl() {
        webView.loadUrl(buildAuthorizationUrl())
    }

    private fun urlEncodeParameter(param: String): String {
        var returnString = ""
        try {
            returnString = URLEncoder.encode(param, "UTF8")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return returnString
    }

    private fun buildAuthorizationUrl(): String {
        var authorizationUrl =
            "$authorizationEndpoint?"
        authorizationUrl += "client_id=" + urlEncodeParameter(clientId)
        authorizationUrl += "&response_type=$responseType"
        authorizationUrl += "&redirect_uri=" + urlEncodeParameter(redirectUri)
        authorizationUrl += "&scope=$scope"
        return authorizationUrl
    }
}