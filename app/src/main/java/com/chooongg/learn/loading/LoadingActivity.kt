package com.chooongg.learn.loading

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.core.ext.hideLoading
import com.chooongg.core.ext.showLoading
import com.chooongg.learn.databinding.ActivityLoadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingActivity : BasicBindingActivity<ActivityLoadingBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.btn.doOnClick {
            lifecycleScope.launch {
                showLoading("Loading会在5秒后关闭")
                delay(5000)
                hideLoading()
            }
        }
    }

}