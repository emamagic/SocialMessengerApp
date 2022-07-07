package com.emamagic.login.user_name

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.common_ui.onTextChange
import com.emamagic.login.contract.LoginRouter
import com.emamagic.login.LoginViewModel
import com.emamagic.login.contract.LoginEvent
import com.emamagic.login.contract.LoginState
import com.emamagic.login.databinding.FragmentLoginViaUsernameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginViaUsernameFragment :
    BaseFragment<FragmentLoginViaUsernameBinding, LoginState, LoginEvent, LoginRouter.Routes, LoginViewModel>()  {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.login_graph)


    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        binding.inputEditTextUsername.onTextChange { viewModel.setEvent(LoginEvent.TypingUserNameOrPassEvent(it, binding.inputEditTextPassword.text.toString())) }
        binding.inputEditTextPassword.onTextChange { viewModel.setEvent(LoginEvent.TypingUserNameOrPassEvent(binding.inputEditTextUsername.text.toString(), it)) }
        binding.btnSubmit.setOnClickListener {
            viewModel.setEvent(LoginEvent.SubmitUserNameEvent(
                binding.inputEditTextUsername.text.toString(),
                binding.inputEditTextPassword.text.toString()))
        }
        binding.txtSignupWithPhone.setOnClickListener { viewModel.setEvent(LoginEvent.LoginWithPhoneNumberClickEvent) }

    }

    override fun renderViewState(viewState: LoginState) {

    }

    override fun enableUiComponent(type: Any?): Boolean {
        binding.btnSubmit.enable()
        return true
    }

    override fun disableUiComponent(type: Any?): Boolean {
        binding.btnSubmit.disable()
        return true
    }

    override fun invalidInput(message: String?, resId: Int?, type: Any?): Boolean {
        type as Boolean
        if (type) binding.validatorInputUsername.invalidateInput()
        else binding.validatorInputPassword.invalidateInput()
        return true
    }


}