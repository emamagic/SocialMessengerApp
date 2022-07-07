package com.emamagic.common_ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class BasePagerAdapter(list: List<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm ,lifecycle) {

    private val fragmentList = list
    private var position: Int = 0

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = fragmentList[position]
        this.position = position
        return fragment
    }

}