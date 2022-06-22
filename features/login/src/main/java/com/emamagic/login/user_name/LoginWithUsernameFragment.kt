package com.emamagic.login.user_name

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.onTextChange
import com.emamagic.base.base.BaseFragment
import com.emamagic.login.LoginViewModel
import com.emamagic.login.contract.LoginAction
import com.emamagic.login.contract.LoginState
import com.emamagic.login.databinding.FragmentLoginWithUsernameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginWithUsernameFragment :
    BaseFragment<FragmentLoginWithUsernameBinding, LoginState, LoginAction, LoginViewModel>()  {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.login_modules)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        binding.inputEditTextUsername.onTextChange { viewModel.typingUserNameOrPassEvent(it, binding.inputEditTextPassword.text.toString()) }
        binding.inputEditTextPassword.onTextChange { viewModel.typingUserNameOrPassEvent(binding.inputEditTextUsername.text.toString(), it) }
        binding.btnSubmit.setOnClickListener {
            viewModel.submitUserNameEvent(
            binding.inputEditTextUsername.text.toString(),
            binding.inputEditTextPassword.text.toString())
        }
        binding.txtSignupWithPhone.setOnClickListener { viewModel.signinWithPhoneClickedEvent() }
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

    override fun emptyInputValue(type: Any?): Boolean {
        type as Boolean
        if (type) binding.validatorInputUsername.invalidateInput()
        else binding.validatorInputPassword.invalidateInput()
        return true
    }
}