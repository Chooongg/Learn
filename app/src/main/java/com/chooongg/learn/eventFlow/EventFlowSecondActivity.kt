package com.chooongg.learn.eventFlow

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.eventflow.observeEventTag
import com.chooongg.eventflow.postEventTag
import com.chooongg.learn.databinding.ActivityEventFlowSecondBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EventFlowSecondActivity:BasicBindingActivity<ActivityEventFlowSecondBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        observeEventTag("测试") {
            binding.tvApplicationText.text = "接收到了全局消息${System.currentTimeMillis()}"
        }
        observeEventTag(activity, "测试") {
            binding.tvRangeText.text = "接收到了 Activity 范围消息${System.currentTimeMillis()}"
        }
        binding.btnSendGlobal.doOnClick {
            lifecycleScope.launch {
                postEventTag("测试")
                it.text = "已发送"
                delay(2000)
                it.text = "发送 全局 范围内消息"
            }
        }
        binding.btnSendRange.doOnClick {
            lifecycleScope.launch {
                postEventTag(activity, "测试")
                it.text = "已发送"
                delay(2000)
                it.text = "发送 Activity 范围内消息"
            }
        }
    }
}