package com.chooongg.learn.mediaPicker

import android.os.Bundle
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.core.ext.startActivity
import com.chooongg.learn.databinding.ActivityMediaPickerBinding
import com.chooongg.mediaPicker.LearnMediaPickerActivity

class MediaPickerDemoActivity : BasicBindingActivity<ActivityMediaPickerBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivImage.doOnClick {
            startActivity(LearnMediaPickerActivity::class, it)
        }
    }

}