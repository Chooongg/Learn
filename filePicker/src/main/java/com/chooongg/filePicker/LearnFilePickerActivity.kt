package com.chooongg.filePicker

import android.os.Bundle
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.filePicker.databinding.LearnActivityFilePickerBinding

class LearnFilePickerActivity : BasicBindingActivity<LearnActivityFilePickerBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.topAppBar)
        title = null
    }

}