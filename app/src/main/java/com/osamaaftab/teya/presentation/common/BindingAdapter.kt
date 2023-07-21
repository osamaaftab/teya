package com.osamaaftab.teya.presentation.common

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.osamaaftab.teya.presentation.adapter.GenericPagerAdapter
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

@BindingAdapter("gallery")
fun ViewPager2.setImageGallery(adapter: GenericPagerAdapter) {
    this.adapter = adapter
}

@BindingAdapter("indicator")
fun WormDotsIndicator.setAdapter(viewPager: ViewPager2) {
    this.setViewPager2(viewPager)
}