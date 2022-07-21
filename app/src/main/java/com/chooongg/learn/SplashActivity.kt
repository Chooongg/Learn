package com.chooongg.learn

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.learn.databinding.ActivitySplashBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BasicBindingActivity<ActivitySplashBinding>() {

    private var job: Job? = null

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(context, MainActivity::class.java))
            finish()
        }
    }

    override fun onPause() {
        if (job?.isActive == true) job?.cancel()
        super.onPause()
    }
}