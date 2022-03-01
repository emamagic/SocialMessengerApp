package com.emamagic.signin.user_name

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.emamagic.application.base.BaseFragment
import com.emamagic.application.extension.onTextChange
import com.emamagic.signin.R
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

        binding.inputEditTextUsername.onTextChange { viewModel.typingUserNameEvent(it) }
        binding.inputEditTextPassword.onTextChange { viewModel.typingPasswordEvent(it) }



    }

    override fun renderViewState(viewState: SigninState) {

    }

    override fun init() {}

    override fun onClickListeners() {

        binding.btnSubmit.setOnClickListener { viewModel.submitUserNameEvent() }

    }
}