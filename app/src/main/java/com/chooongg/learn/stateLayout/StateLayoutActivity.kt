package com.chooongg.learn.stateLayout

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.learn.R
import com.chooongg.learn.databinding.ActivityStateLayoutBinding
import com.chooongg.stateLayout.state.LineProgressState
import com.chooongg.stateLayout.state.ProgressState

class StateLayoutActivity : BasicBindingActivity<ActivityStateLayoutBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.stateLayout
        binding.btnProgress.doOnClick {
            binding.stateLayout.showState(ProgressState::class)
        }

        binding.btnProgressLine.doOnClick {
            binding.stateLayout.showState(LineProgressState::class)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.state_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.success) {
            binding.stateLayout.showSuccess()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}