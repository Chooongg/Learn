package com.chooongg.learn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chooongg.basic.ext.doOnClick
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<FloatingActionButton>(R.id.fab).doOnClick {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}