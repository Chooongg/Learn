package com.chooongg.learn.network

import androidx.lifecycle.lifecycleScope
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.learn.api.WanAndroidAPI
import com.chooongg.learn.databinding.ActivityNetworkBinding
import com.chooongg.net.ext.launchRequest

class NetworkActivity : BasicBindingActivity<ActivityNetworkBinding>() {

    override fun onRefresh(any: Any?) {
        lifecycleScope.launchRequest {
            api { WanAndroidAPI.service.getPackageList() }
            onSuccess {

            }
        }
    }
}