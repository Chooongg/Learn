package com.chooongg.learn.eventFlow

import android.os.Bundle
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.eventflow.observeEventTag
import com.chooongg.eventflow.postEventTag
import com.chooongg.learn.databinding.ActivityEventFlowBinding

class EventFlowActivity : BasicBindingActivity<ActivityEventFlowBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        observeEventTag(activity, "测试") {
            binding.tvText.text = "接收到了当前Activity的Event"
        }
        binding.btnPostEventInCurrentActivity.doOnClick {
            binding.tvText.text = "发送当前Activity范围的Event事件,延时2秒"
            postEventTag(activity, "测试", 2000L)
        }
        observeEventTag("测试") {
            binding.tvTextApplication.text = "接收到了Event"
        }
        binding.btnSecondPage.doOnClick {

        }
    }
}