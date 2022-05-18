package com.emamagic.signin.user_name

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.androidcore.onTextChange
import com.emamagic.mvi.BaseEffect
import com.emamagic.base.base.BaseFragment
import com.emamagic.mvi.SigninEffect
import com.emamagic.signin.SigninViewModel
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.databinding.FragmentSigninWithUserNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninWithUserNameFragment :
    BaseFragment<FragmentSigninWithUserNameBinding, SigninState, SigninAction, SigninViewModel>()  {

    override val viewModel: SigninViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.singin_modules)

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

    override fun renderViewState(viewState: SigninState) {

    }

    override fun renderCustomViewEffect(viewEffect: BaseEffect): Boolean {
        when (viewEffect) {
            SigninEffect.InvalidUsername -> binding.validatorInputUsername.invalidateInput()
            SigninEffect.InvalidPass -> binding.validatorInputPassword.invalidateInput()
            else -> {}
        }
        return true
    }

    override fun enableUiComponent(): Boolean {
        binding.btnSubmit.enable()
        return true
    }

    override fun disableUiComponent(): Boolean {
        binding.btnSubmit.disable()
        return true
    }
}