package com.emamagic.login.server_name

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.core_android.keycloak.AuthStateManager
import com.emamagic.login.contract.LoginRouter
import com.emamagic.login.LoginViewModel
import com.emamagic.login.contract.LoginEvent
import com.emamagic.login.contract.LoginState
import com.emamagic.login.databinding.FragmentChangeServerNameBinding
import com.emamagic.mvi.BaseEffect
import com.emamagic.mvi.LoginEffect
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.*
import javax.inject.Inject

@AndroidEntryPoint
class ChangeServerNameFragment :
    BaseFragment<FragmentChangeServerNameBinding, LoginState, LoginEvent, LoginRouter.Routes, LoginViewModel>() {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.login_graph)

    @Inject
    lateinit var lazyAuthService: Lazy<AuthorizationService>
    @Inject
    lateinit var lazyAuthManager: Lazy<AuthStateManager>


    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnSubmit.setOnClickListener {
            viewModel.setEvent(LoginEvent.ChangeServerNameEvent(binding.inputEditText.text.toString()))
        }

    }

    override fun renderViewState(viewState: LoginState) {

    }

    override fun renderCustomViewEffect(viewEffect: BaseEffect): Boolean {
        when (viewEffect) {
            is LoginEffect.Keycloak -> SSlCertificateChecker(
                requireActivity(),
                viewEffect.authorizationEndpoint,
                viewEffect.tokenEndpoint,
                viewEffect.keycloakRedirectUri,
                viewEffect.keycloakResource,
                viewEffect.keycloakScope,
                viewEffect.responseType,
                viewModel::processSSlCertResult
            )
            is LoginEffect.PerformAuthorization -> performAuthorization(
                viewEffect.authorizationEndpoint,
                viewEffect.tokenEndpoint,
                viewEffect.keycloakRedirectUri,
                viewEffect.keycloakResource,
                viewEffect.keycloakScope
            )
            else -> {}
        }
        return true
    }

    override fun invalidInput(message: String?, type: Any?): Boolean {
        binding.validatorInput.invalidateInput()
        return true
    }

    private fun performAuthorization(
        authorizationEndpoint: String,
        tokenEndpoint: String,
        keycloakRedirectUri: String,
        keycloakResource: String,
        keycloakScope: String,
    ) {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse(authorizationEndpoint),
            Uri.parse(tokenEndpoint)
        )
        val authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,
            keycloakResource,
            ResponseTypeValues.CODE,
            Uri.parse(keycloakRedirectUri)
        ).setScope(keycloakScope)

        val authRequest = authRequestBuilder.build()
            // todo hide loading
        val authIntent: Intent = lazyAuthService.get().getAuthorizationRequestIntent(authRequest)
        launchCustomTab(authIntent)
    }



    private fun launchCustomTab(authIntent: Intent) {
        val authLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                // todo hide loading
                val resp = AuthorizationResponse.fromIntent(data)
                val ex = AuthorizationException.fromIntent(data)
                if (resp != null) {
                    lazyAuthManager.get().updateAfterAuthorization(resp, ex)
                    lazyAuthService.get().performTokenRequest(resp.createTokenExchangeRequest()) { response, ex1 ->
                        if (response != null) {
                            lazyAuthManager.get().updateAfterTokenResponse(response, ex1)
                            // todo authenticate successFully completed
                            viewModel.setEvent(LoginEvent.LoginWithKeycloakEvent)
                        } else {
                            if (ex1?.type == AuthorizationException.TYPE_GENERAL_ERROR) {
                                // todo hide loading
                            }
                            Toast.makeText(requireContext(), "error certificate", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    // todo hide loading
                }
            } else {
                // todo hide loading
            }
        }
        authLauncher.launch(authIntent)
    }

}