package com.chooongg.learn.topAppBar

import android.content.Intent
import android.os.Bundle
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.learn.databinding.ActivityTopAppBarBinding

class TopAppBarActivity : BasicBindingActivity<ActivityTopAppBarBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnSmall.doOnClick {
            startActivity(Intent(context, TopAppBarLayoutActivity::class.java).apply {
                putExtra("type", "small")
            })
        }
        binding.btnMedium.doOnClick {
            startActivity(Intent(context, TopAppBarLayoutActivity::class.java).apply {
                putExtra("type", "medium")
            })
        }
        binding.btnLarge.doOnClick {
            startActivity(Intent(context, TopAppBarLayoutActivity::class.java).apply {
                putExtra("type", "large")
            })
        }
    }

}