package com.emamagic.signup.intro

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.viewpager2.widget.ViewPager2
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.common_ui.base.BasePagerAdapter
import com.emamagic.core_android.gone
import com.emamagic.core_android.visible
import com.emamagic.signup.R
import com.emamagic.signup.SignupViewModel
import com.emamagic.signup.contract.SignupEvent
import com.emamagic.signup.contract.SignupRouter
import com.emamagic.signup.contract.SignupState
import com.emamagic.signup.databinding.FragmentIntroParentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroParentFragment: BaseFragment<FragmentIntroParentBinding, SignupState, SignupEvent, SignupRouter.Routes, SignupViewModel>() {

    override val viewModel: SignupViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.signup_graph)

    private val justMemberOfOrganization: Boolean = false
    private lateinit var viewPagerCallback: ViewPager2.OnPageChangeCallback

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        val fragmentList = arrayListOf(
            IntroChildFragment(
                R.layout.fragment_intro_one,
                justMemberOfOrganization
            ),
            IntroChildFragment(
                R.layout.fragment_intro_two,
                justMemberOfOrganization
            ),
            IntroChildFragment(
                R.layout.fragment_intro_three,
                justMemberOfOrganization
            ),
        )

        val adapter = BasePagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        binding.pagerIntroF.adapter = adapter
        binding.dotsIndicator.setViewPager2(binding.pagerIntroF)

        viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) { // last IntroScreen
                    binding.btnSignupFSubmit.visible()
                    binding.btnIntroSkip.gone()
                    binding.btnIntroNex.gone()
                    if (justMemberOfOrganization) {
                        binding.btnSignupFSubmit.text = getString(R.string.submit_server_name)
                    }
                } else {
                    binding.btnSignupFSubmit.gone()
                    binding.btnIntroSkip.visible()
                    binding.btnIntroNex.visible()
                }
            }
        }


        binding.btnIntroNex.setOnClickListener {
            binding.pagerIntroF.currentItem = binding.pagerIntroF.currentItem + 1
        }

        binding.btnIntroSkip.setOnClickListener {
            binding.pagerIntroF.currentItem = 2
        }

        binding.btnSignupFSubmit.setOnClickListener {
            viewModel.setEvent(SignupEvent.UserSawIntro)
        }

    }

    override fun renderViewState(viewState: SignupState) {

    }

    override fun onStart() {
        super.onStart()
        binding.pagerIntroF.registerOnPageChangeCallback(viewPagerCallback)
    }

    override fun onStop() {
        super.onStop()
        if (this::viewPagerCallback.isInitialized)
            binding.pagerIntroF.unregisterOnPageChangeCallback(viewPagerCallback)
    }

}