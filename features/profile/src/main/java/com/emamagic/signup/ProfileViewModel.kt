package com.emamagic.signup

import com.emamagic.base.base.BaseViewModel
import com.emamagic.signup.contract.ProfileAction
import com.emamagic.signup.contract.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): BaseViewModel<ProfileState, ProfileAction, ProfileRouter.Routes>() {

    override fun createInitialState(): ProfileState {
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: ProfileAction) {
        TODO("Not yet implemented")
    }


}