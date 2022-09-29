package com.chooongg.filePicker

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.filePicker.databinding.LearnActivityFilePickerBinding
import com.google.android.material.tabs.TabLayout

class LearnFilePickerActivity : BasicBindingActivity<LearnActivityFilePickerBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.topAppBar)
        title = null
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

}