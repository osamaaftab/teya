package com.osamaaftab.teya.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.osamaaftab.teya.presentation.common.PagerItem

abstract class GenericPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    protected abstract fun getViewsCount(): Int
    protected abstract fun getPagerItem(): ArrayList<PagerItem>

    override fun getItemCount(): Int = getViewsCount()

    override fun createFragment(position: Int): Fragment {
       return getPagerItem()[position].fragment
    }
}