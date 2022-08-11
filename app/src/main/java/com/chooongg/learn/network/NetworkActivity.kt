package com.chooongg.learn.network

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.core.adapter.BindingAdapter
import com.chooongg.learn.api.WanAndroidAPI
import com.chooongg.learn.databinding.ActivityNetworkBinding
import com.chooongg.learn.databinding.ItemNetworkBinding
import com.chooongg.net.ext.launchRequest
import com.chooongg.stateLayout.state.ProgressState

class NetworkActivity : BasicBindingActivity<ActivityNetworkBinding>() {

    private val adapter = Adapter()

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
    }

    override fun initContent(savedInstanceState: Bundle?) {
        onRefresh()
    }

    override fun onRefresh(any: Any?) {
        lifecycleScope.launchRequest {
            api { WanAndroidAPI.service.getPackageList() }
            onStart {
                binding.stateLayout.show(ProgressState::class)
            }
            onSuccess {
                adapter.setNewInstance(it)
                binding.stateLayout.showSuccess()
            }
            onError {
                it.printStackTrace()
            }
        }
    }

    private class Adapter : BindingAdapter<String, ItemNetworkBinding>() {
        override fun convert(holder: BaseViewHolder, binding: ItemNetworkBinding, item: String) {
            binding.tvName.text = item
        }
    }
}