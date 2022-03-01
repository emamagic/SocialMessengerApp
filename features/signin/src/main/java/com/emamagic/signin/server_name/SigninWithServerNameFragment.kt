package com.emamagic.signin.server_name

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.emamagic.application.base.BaseFragment
import com.emamagic.signin.SigninViewModel
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.databinding.FragmentSigninWithServerNameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninWithServerNameFragment :
    BaseFragment<FragmentSigninWithServerNameBinding, SigninState, SigninAction, SigninViewModel>() {

    override val viewModel: SigninViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.singin_modules)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun renderViewState(viewState: SigninState) {
        TODO("Not yet implemented")
    }

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun onClickListeners() {
        TODO("Not yet implemented")
    }
}