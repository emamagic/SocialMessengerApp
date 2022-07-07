package com.emamagic.signup.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.emamagic.signup.R
import com.emamagic.signup.databinding.FragmentIntroOneBinding
import com.emamagic.signup.databinding.FragmentIntroThreeBinding
import com.emamagic.signup.databinding.FragmentIntroTwoBinding

// todo changed to fragment factory for input at constructor
class IntroChildFragment(
    @LayoutRes private val layout: Int,
    private val justMemberOfOrganization: Boolean? = false
) : Fragment(layout) {

    private lateinit var thirdChildFragmentBinding: FragmentIntroThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when (layout) {
            R.layout.fragment_intro_one -> {
                val binding = FragmentIntroOneBinding.inflate(inflater, container, false)
                binding.lifecycleOwner = viewLifecycleOwner
                binding.root
            }
            R.layout.fragment_intro_two -> {
                val binding = FragmentIntroTwoBinding.inflate(inflater, container, false)
                binding.lifecycleOwner = viewLifecycleOwner
                binding.root
            }
            R.layout.fragment_intro_three -> {
                thirdChildFragmentBinding = FragmentIntroThreeBinding.inflate(inflater, container, false)
                thirdChildFragmentBinding.lifecycleOwner = viewLifecycleOwner
                thirdChildFragmentBinding.root
            }
            else -> null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (layout == R.layout.fragment_intro_three && justMemberOfOrganization == true) {
            thirdChildFragmentBinding.appCompatTextView8.text =
                getString(R.string.intro_three_title_when_only_define_organization)
            thirdChildFragmentBinding.appCompatTextView9.text =
                getString(R.string.intro_three_description_when_only_define_organization)
        }
    }



}