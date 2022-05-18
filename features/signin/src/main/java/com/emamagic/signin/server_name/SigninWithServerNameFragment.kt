package com.emamagic.signin.server_name

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.base.BaseFragment
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

    }

    override fun renderViewState(viewState: SigninState) {

    }


}