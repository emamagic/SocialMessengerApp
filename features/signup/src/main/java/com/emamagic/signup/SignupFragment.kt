package com.emamagic.signup

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.signup.contract.SignupEvent
import com.emamagic.signup.contract.SignupRouter
import com.emamagic.signup.contract.SignupState
import com.emamagic.signup.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment: BaseFragment<FragmentSignupBinding, SignupState, SignupEvent, SignupRouter.Routes, SignupViewModel>() {

    override val viewModel: SignupViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.signup_graph)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
            viewModel.setEvent(SignupEvent.UserPickedAvatar(it))
        }
        binding.imgProfileFLogo.setOnClickListener {
            getContent.launch("image/*")
        }
    }

    override fun renderViewState(viewState: SignupState) {

    }
}