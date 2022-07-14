package com.chooongg.learn

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.setNightMode
import com.chooongg.core.activity.BasicBindingModelActivity
import com.chooongg.core.adapter.BindingAdapter
import com.chooongg.core.annotation.HomeButton
import com.chooongg.core.ext.divider
import com.chooongg.core.ext.doOnItemClick
import com.chooongg.core.ext.showAllDivider
import com.chooongg.core.viewModel.BasicModel
import com.chooongg.learn.databinding.ActivityMainBinding
import com.chooongg.learn.databinding.ItemMainBinding
import com.chooongg.learn.stateLayout.StateLayoutActivity
import com.chooongg.learn.topAppBar.TopAppBarActivity
import com.hjq.permissions.Permission

@HomeButton(false)
class MainActivity : BasicBindingModelActivity<ActivityMainBinding, BasicModel>() {

    private val adapter = Adapter()

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.divider {
            asSpace().showAllDivider()
            size(resDimensionPixelSize(com.chooongg.basic.R.dimen.contentMedium))
        }
        adapter.doOnItemClick { _, _, position ->
            when (adapter.data[position].icon) {
                R.drawable.ic_main_state_layout -> {
                    startActivity(Intent(context, StateLayoutActivity::class.java))
                }
                R.drawable.ic_main_top_app_bar -> {
                    startActivity(Intent(context, TopAppBarActivity::class.java))
                }
            }
        }
    }

    override fun initContent(savedInstanceState: Bundle?) {
        adapter.setNewInstance(
            mutableListOf(
                MainItem(R.drawable.ic_main_state_layout, "StateLayout"),
                MainItem(R.drawable.ic_main_top_app_bar, "TopAppBar")
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

    data class MainItem(val icon: Int, val title: String)

    private class Adapter : BindingAdapter<MainItem, ItemMainBinding>() {
        override fun convert(holder: BaseViewHolder, binding: ItemMainBinding, item: MainItem) {
            binding.btn.text = item.title
            binding.btn.setIconResource(item.icon)
        }
    }
}