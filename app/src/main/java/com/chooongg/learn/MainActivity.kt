package com.chooongg.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.setNightMode
import com.chooongg.core.activity.BasicBindingModelActivity
import com.chooongg.core.viewModel.BasicModel
import com.chooongg.learn.databinding.ActivityMainBinding
import com.chooongg.learn.databinding.ItemMainBinding

class MainActivity : BasicBindingModelActivity<ActivityMainBinding, BasicModel>() {

    private val adapter = Adapter(
        mutableListOf(
            MainItem(com.chooongg.core.R.drawable.ic_top_app_bar_close, "TopAppBar"),
            MainItem(com.chooongg.core.R.drawable.ic_top_app_bar_close, "StateLayout")
        )
    )

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
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

    private class Adapter(var data: MutableList<MainItem>? = null) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {

        class ViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.binding.ivIcon.setImageResource(data!![position].icon)
            holder.binding.tvTitle.text = data!![position].title
        }

        override fun getItemCount() = data?.size ?: 0
    }
}