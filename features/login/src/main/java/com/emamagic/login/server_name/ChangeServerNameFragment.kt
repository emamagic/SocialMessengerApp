package com.emamagic.login.server_name

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.base.BaseFragment
import com.emamagic.login.LoginViewModel
import com.emamagic.login.contract.LoginAction
import com.emamagic.login.contract.LoginState
import com.emamagic.login.databinding.FragmentChangeServerNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeServerNameFragment :
    BaseFragment<FragmentChangeServerNameBinding, LoginState, LoginAction, LoginViewModel>() {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.login_modules)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewModel = viewModel
    }

    override fun renderViewState(viewState: LoginState) {

    }

    override fun invalidInputValue(message: String?, type: Any?): Boolean {
        binding.validatorInput.invalidateInput()
        return true
    }


}