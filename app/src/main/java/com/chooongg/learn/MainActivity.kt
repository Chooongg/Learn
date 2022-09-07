package com.chooongg.learn

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.setNightMode
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.core.adapter.BindingAdapter
import com.chooongg.core.annotation.HomeButton
import com.chooongg.core.ext.divider
import com.chooongg.core.ext.showAllDivider
import com.chooongg.core.ext.startActivity
import com.chooongg.learn.databinding.ActivityMainBinding
import com.chooongg.learn.databinding.ItemMainBinding
import com.chooongg.learn.echarts.EChartsActivity
import com.chooongg.learn.eventFlow.EventFlowActivity
import com.chooongg.learn.loading.LoadingActivity
import com.chooongg.learn.network.NetworkActivity
import com.chooongg.learn.stateLayout.StateLayoutActivity
import com.chooongg.learn.topAppBar.TopAppBarActivity

@HomeButton(false)
class MainActivity : BasicBindingActivity<ActivityMainBinding>() {

    private val adapter = Adapter()

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.divider {
            asSpace().showAllDivider()
            size(resDimensionPixelSize(com.chooongg.basic.R.dimen.contentMedium))
        }
        adapter.setOnItemClickListener { _, view, position ->
            adapter.data[position].action(view)
        }
        adapter.setNewInstance(
            mutableListOf(
                MainItem(R.drawable.ic_main_echarts, "ECharts") {
                    startActivity(EChartsActivity::class, it)
                }, MainItem(R.drawable.ic_main_event_flow, "EventFlow") {
                    startActivity(EventFlowActivity::class, it)
                }, MainItem(R.drawable.ic_main_loading, "Loading") {
                    startActivity(LoadingActivity::class, it)
                }, MainItem(R.drawable.ic_main_state_layout, "StateLayout") {
                    startActivity(StateLayoutActivity::class, it)
                }, MainItem(R.drawable.ic_main_network, "Network") {
                    startActivity(NetworkActivity::class, it)
                }, MainItem(R.drawable.ic_main_top_app_bar, "TopAppBar") {
                    startActivity(TopAppBarActivity::class, it)
                }
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.night_mode, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.light -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
            R.id.dark -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            R.id.system -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    data class MainItem(val icon: Int, val title: String, val action: (View) -> Unit)

    private class Adapter : BindingAdapter<MainItem, ItemMainBinding>() {
        override fun convert(holder: BaseViewHolder, binding: ItemMainBinding, item: MainItem) {
            binding.btn.text = item.title
            binding.btn.setIconResource(item.icon)
        }
    }
}