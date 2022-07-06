package com.chooongg.learn

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.basic.ext.setNightMode
import com.chooongg.core.activity.BasicBindingModelActivity
import com.chooongg.core.viewModel.BasicModel
import com.chooongg.learn.databinding.ActivityMainBinding

class MainActivity : BasicBindingModelActivity<ActivityMainBinding, BasicModel>() {

    override fun initView(savedInstanceState: Bundle?) {
        title = "Learn首页asdfasdfasdf awefa wef "
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
}