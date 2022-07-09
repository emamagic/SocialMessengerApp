package com.emamagic.signup

import android.net.Uri
import android.util.Log
import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.core.LimooHttpCode
import com.emamagic.core.exhaustive
import com.emamagic.core.succeeded
import com.emamagic.core_android.ToastScope
import com.emamagic.domain.interactors.*
import com.emamagic.mvi.BaseEffect
import com.emamagic.signup.contract.SignupEvent
import com.emamagic.signup.contract.SignupRouter
import com.emamagic.signup.contract.SignupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val introStatus: IntroStatus,
    private val loginProcess: CheckLoginProcess,
    private val signupUser: SignupUser,
    private val uploadFile: UploadFile
) : BaseViewModel<SignupState, SignupEvent, SignupRouter.Routes>() {

    init {
        setEffect { BaseEffect.HideLoading(scope = ToastScope.MODULE_SCOPE) }

    }

    private fun init() = withLoadingScope {
        setEffect { BaseEffect.ShowLoading(scope = ToastScope.MODULE_SCOPE) }
        getCurrentUser(Unit).manageResult(success = {
            if (!introStatus.hasIntroBeenSeen()) {
                routerDelegate.pushRoute(SignupRouter.Routes.ToIntro)
                setEffect { BaseEffect.HideLoading(scope = ToastScope.MODULE_SCOPE) }
            } else { // unknown state
                TODO()
            }
        }, failed = {
            if (it.statusCode == LimooHttpCode.HTTP_SIGNUP) {
                setEffect { BaseEffect.HideLoading(scope = ToastScope.MODULE_SCOPE) }
            } else { // unknown state
                TODO()
            }
        })

    }

    override fun createInitialState(): SignupState = SignupState.initialize()

    override fun handleEvent(event: SignupEvent) {
        when (event) {
            SignupEvent.UserSawIntro -> userSawIntro()
            is SignupEvent.UserPickedAvatar -> uploadAvatar(event.uri)
        }
    }

    private fun uploadAvatar(uri: Uri) = withLoadingScope {
        uploadFile(uri.toString()).collect {
            if (it.succeeded) {
                setState { copy(avatarHash = it.data!![0].hash) }
            }
        }
    }

    private fun signup(firstName: String, lastName: String, email: String, avatarHash: String) =
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
        initProcess()
    }

    private suspend fun initProcess() {
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
            CheckLoginProcess.LoginProcessResult.GoToIntro -> TODO()
        }.exhaustive
    }

}