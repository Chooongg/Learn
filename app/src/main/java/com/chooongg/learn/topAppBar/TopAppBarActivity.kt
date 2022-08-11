package com.chooongg.learn.topAppBar

import android.os.Bundle
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.core.ext.startActivity
import com.chooongg.learn.databinding.ActivityTopAppBarBinding

class TopAppBarActivity : BasicBindingActivity<ActivityTopAppBarBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnSmall.doOnClick {
            startActivity(TopAppBarLayoutActivity::class, it) {
                putExtra("type", "small")
            }
        }
        binding.btnMedium.doOnClick {
            startActivity(TopAppBarLayoutActivity::class, it) {
                putExtra("type", "medium")
            }
        }
        binding.btnLarge.doOnClick {
            startActivity(TopAppBarLayoutActivity::class, it) {
                putExtra("type", "large")
            }
        }
    }

}