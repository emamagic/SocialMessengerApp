package com.emamagic.signup

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import coil.load
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.signup.contract.SignupEvent
import com.emamagic.signup.contract.SignupRouter
import com.emamagic.signup.contract.SignupState
import com.emamagic.signup.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment: BaseFragment<FragmentSignupBinding, SignupState, SignupEvent, SignupRouter.Routes, SignupViewModel>() {

    override val viewModel: SignupViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.signup_graph)

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.setEvent(SignupEvent.UserPickedAvatar(it))
    }

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        binding.imgProfileFLogo.setOnClickListener { getContent.launch("image/*") }

        binding.btnSubmit.setOnClickListener {
            viewModel.setEvent(SignupEvent.Signup(binding.inputEditTextUsername.text.toString(), binding.inputEditTextFamily.text.toString(), binding.inputEditTextEmail.text.toString()))
        }


    }

    override fun renderViewState(viewState: SignupState) {
        if (!viewState.avatarUrl.isNullOrEmpty()) {
            binding.imgProfileFLogo.load(viewState.avatarUrl)
        }
    }

    override fun invalidInput(message: String?, resId: Int?, type: Any?): Boolean {
        when (type) {
            SignupState.FIRST_NAME_INVALID -> binding.validatorInputUsername.invalidate()
            SignupState.LAST_NAME_INVALID -> binding.validatorTextFamily.invalidate()
            SignupState.EMAIL_INVALID -> binding.validatorInputEmail.invalidateInput()
        }
        return true
    }
}