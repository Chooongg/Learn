package com.chooongg.learn.eventFlow

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.fragment.BasicBindingFragment
import com.chooongg.eventFlow.observeEventTag
import com.chooongg.eventFlow.postEventTag
import com.chooongg.learn.databinding.FragmentEventFlowBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EventFlowFragment : BasicBindingFragment<FragmentEventFlowBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        observeEventTag("测试") {
            binding.tvApplicationText.text = "接收到了全局消息${System.currentTimeMillis()}"
        }
        observeEventTag(fragment, "测试") {
            binding.tvRangeText.text = "接收到了 Fragment 范围消息${System.currentTimeMillis()}"
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
                postEventTag(fragment, "测试")
                it.text = "已发送"
                delay(2000)
                it.text = "发送 Fragment 范围内消息"
            }
        }
    }
}