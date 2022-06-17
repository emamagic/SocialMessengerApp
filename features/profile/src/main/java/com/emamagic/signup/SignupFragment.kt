package com.emamagic.signup

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.base.BaseFragment
import com.emamagic.signup.contract.SignupAction
import com.emamagic.signup.contract.SignupState
import com.emamagic.signup.databinding.FragmentSignupBinding

class SignupFragment: BaseFragment<FragmentSignupBinding, SignupState, SignupAction, SignupViewModel>() {

    override val viewModel: SignupViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.signup_graph)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun renderViewState(viewState: SignupState) {

    }


}