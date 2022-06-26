package com.emamagic.splash

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.base.BaseFragment
import com.emamagic.splash.contract.SplashEvent
import com.emamagic.splash.contract.SplashState
import com.emamagic.splash.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment: BaseFragment<FragmentSplashBinding, SplashState, SplashEvent, SplashRouter.Routes, SplashViewModel>() {

    override val viewModel: SplashViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.splash_graph)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun renderViewState(viewState: SplashState) {

    }

}