package com.emamagic.signup

import android.net.Uri
import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.core.LimooHttpCode
import com.emamagic.core.ResultWrapper
import com.emamagic.core.exhaustive
import com.emamagic.core.succeeded
import com.emamagic.core_android.ToastScope
import com.emamagic.domain.interactors.*
import com.emamagic.domain.interactors.login.CheckLoginProcess
import com.emamagic.domain.interactors.login.IntroStatus
import com.emamagic.domain.interactors.login.SignupUser
import com.emamagic.mvi.BaseEffect
import com.emamagic.signup.contract.SignupEvent
import com.emamagic.signup.contract.SignupRouter
import com.emamagic.signup.contract.SignupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val introStatus: IntroStatus,
    private val loginProcess: CheckLoginProcess,
    private val signupUser: SignupUser,
    private val uploadFile: UploadFile
) : BaseViewModel<SignupState, SignupEvent, SignupRouter.Routes>() {

    private var avatarHash: String? = null
    init { init() }

    private fun init() = withLoadingScope {
        setEffect { BaseEffect.ShowLoading(scope = ToastScope.MODULE_SCOPE) }
        when (val result = getCurrentUser(Unit)) {
            is ResultWrapper.FetchLoading -> TODO()
            is ResultWrapper.Success -> TODO()
            is ResultWrapper.Failed -> {
                if (result.error?.status_code == LimooHttpCode.HTTP_SIGNUP) {
                    setEffect { BaseEffect.HideLoading(scope = ToastScope.MODULE_SCOPE) }
                } else { // unknown state
                    TODO()
                }
            }
        }
    }

    override fun createInitialState(): SignupState = SignupState.initialize()

    override fun handleEvent(event: SignupEvent) {
        when (event) {
            SignupEvent.UserSawIntro -> userSawIntro()
            is SignupEvent.UserPickedAvatar -> uploadUserAvatar(event.uri)
            is SignupEvent.Signup -> validateIfNeeded(event.firstName, event.lastName, event.email)
        }
    }

    private fun validateIfNeeded(firstName: String?, lastName: String?, email: String?) {
        if (firstName.isNullOrEmpty()) {
            setEffect { BaseEffect.InvalidInput(type = SignupState.FIRST_NAME_INVALID) }
            return
        }
        if (lastName.isNullOrEmpty()) {
            setEffect { BaseEffect.InvalidInput(type = SignupState.LAST_NAME_INVALID) }
            return
        }
        if (email.isNullOrEmpty()) {
            setEffect { BaseEffect.InvalidInput(type = SignupState.EMAIL_INVALID) }
            return
        }
        signup(firstName, lastName, email)
    }


    private fun uploadUserAvatar(uri: Uri) = withLoadingScope {
        uploadFile(uri.toString()).collect {
            if (it.succeeded) {
                val attachment = it.data!![0]
                avatarHash = attachment.hash
                setState { copy(avatarUrl = attachment.url) }
            }
        }
    }

    private fun signup(firstName: String, lastName: String, email: String) =
        withLoadingScope {
            signupUser(
                SignupUser.Params(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    avatarHash = avatarHash
                )
            ).manageResult(success = {
                routerDelegate.pushRoute(SignupRouter.Routes.ToIntro)
            })
        }

    private fun userSawIntro() = withLoadingScope {
        introStatus.userSawIntro()
        userProcess()
    }

    private suspend fun userProcess() {
        setEffect { BaseEffect.ShowLoading(scope = ToastScope.MODULE_SCOPE) }
        when (loginProcess()) {
            CheckLoginProcess.LoginProcessResult.GoToConversation -> routerDelegate.pushRoute(
                SignupRouter.Routes.ToConversation
            )
            CheckLoginProcess.LoginProcessResult.GoToWorkspaceCreate -> routerDelegate.pushRoute(
                SignupRouter.Routes.ToWorkspaceCreate
            )
            CheckLoginProcess.LoginProcessResult.GoToWorkspaceSelect -> routerDelegate.pushRoute(
                SignupRouter.Routes.ToWorkspaceSelect
            )
        }.exhaustive
    }

}