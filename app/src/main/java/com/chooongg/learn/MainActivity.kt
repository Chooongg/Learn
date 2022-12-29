package com.chooongg.learn

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.basic.ext.getNightMode
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.resInteger
import com.chooongg.basic.ext.setNightMode
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.core.adapter.BindingAdapter
import com.chooongg.core.annotation.TopAppbarNavigationButton
import com.chooongg.core.ext.divider
import com.chooongg.core.ext.startActivity
import com.chooongg.filePicker.LearnFilePickerActivity
import com.chooongg.learn.databinding.ActivityMainBinding
import com.chooongg.learn.databinding.ItemMainBinding
import com.chooongg.learn.echarts.EChartsActivity
import com.chooongg.learn.eventFlow.EventFlowActivity
import com.chooongg.learn.form.FormActivity
import com.chooongg.learn.loading.LoadingActivity
import com.chooongg.learn.mediaPicker.MediaPickerDemoActivity
import com.chooongg.learn.network.NetworkActivity
import com.chooongg.learn.platte.PlatteActivity
import com.chooongg.learn.popupMenu.PopupMenuActivity
import com.chooongg.learn.stateLayout.StateLayoutActivity
import com.chooongg.learn.topAppBar.TopAppBarActivity

@TopAppbarNavigationButton(false)
class MainActivity : BasicBindingActivity<ActivityMainBinding>() {

    private val adapter = Adapter()

    override fun initView(savedInstanceState: Bundle?) {
        binding.navigationView.post {
            binding.recyclerView.updateLayoutParams<MarginLayoutParams> {
                bottomMargin = binding.navigationView.height
            }
        }
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, resInteger(R.integer.main_grid_layout_span))
        binding.recyclerView.divider {
            asSpace().size(resDimensionPixelSize(com.chooongg.basic.R.dimen.contentMedium))
            showFirstDivider().showLastDivider().showSideDividers()
        }
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener { _, view, position ->
            adapter.data[position].action(view)
        }
        when (getNightMode()) {
            AppCompatDelegate.MODE_NIGHT_NO ->
                binding.navigationView.selectedItemId = R.id.light
            AppCompatDelegate.MODE_NIGHT_YES ->
                binding.navigationView.selectedItemId = R.id.dark
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM ->
                binding.navigationView.selectedItemId = R.id.system
        }
        binding.navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.light -> setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                R.id.dark -> setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                R.id.system -> setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    override fun initContent(savedInstanceState: Bundle?) {
        adapter.setNewInstance(
            mutableListOf(
                MainItem(R.drawable.ic_main_echarts, "ECharts") {
                    startActivity(EChartsActivity::class, it)
                },
                MainItem(R.drawable.ic_main_event_flow, "EventFlow") {
                    startActivity(EventFlowActivity::class, it)
                },
                MainItem(R.drawable.ic_main_file_picker, "FilePicker") {
                    startActivity(LearnFilePickerActivity::class, it)
                },
                MainItem(R.drawable.ic_main_form, "Form") {
                    startActivity(FormActivity::class, it)
                },
                MainItem(R.drawable.ic_main_loading, "Loading") {
                    startActivity(LoadingActivity::class, it)
                },
                MainItem(R.drawable.ic_main_media_picker, "MediaPicker") {
                    startActivity(MediaPickerDemoActivity::class, it)
                },
                MainItem(R.drawable.ic_main_network, "Network") {
                    startActivity(NetworkActivity::class, it)
                },
                MainItem(R.drawable.ic_main_platte, "Platte") {
                    startActivity(PlatteActivity::class, it)
                },
                MainItem(R.drawable.ic_main_popup_menu, "PopupMenu") {
                    startActivity(PopupMenuActivity::class, it)
                },
                MainItem(R.drawable.ic_main_state_layout, "StateLayout") {
                    startActivity(StateLayoutActivity::class, it)
                },
                MainItem(R.drawable.ic_main_top_app_bar, "TopAppBar") {
                    startActivity(TopAppBarActivity::class, it)
                }
            )
        )
    }

    data class MainItem(val icon: Int, val title: String, val action: (View) -> Unit)

    private class Adapter : BindingAdapter<MainItem, ItemMainBinding>() {
        override fun convert(holder: BaseViewHolder, binding: ItemMainBinding, item: MainItem) {
            binding.btn.text = item.title
            binding.btn.setIconResource(item.icon)
        }
    }
}