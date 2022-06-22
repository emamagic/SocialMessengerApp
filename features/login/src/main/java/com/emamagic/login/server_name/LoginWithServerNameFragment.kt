package com.emamagic.login.server_name

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.base.BaseFragment
import com.emamagic.login.LoginViewModel
import com.emamagic.login.contract.LoginAction
import com.emamagic.login.contract.LoginState
import com.emamagic.login.databinding.FragmentLoginWithServerNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginWithServerNameFragment :
    BaseFragment<FragmentLoginWithServerNameBinding, LoginState, LoginAction, LoginViewModel>() {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.login_modules)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun renderViewState(viewState: LoginState) {

    }


}