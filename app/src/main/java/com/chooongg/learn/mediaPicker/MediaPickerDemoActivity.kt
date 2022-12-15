package com.chooongg.learn.mediaPicker

import android.os.Bundle
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.learn.databinding.ActivityMediaPickerBinding

class MediaPickerDemoActivity : BasicBindingActivity<ActivityMediaPickerBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivImage.doOnClick {
        }
    }

}