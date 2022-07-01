package com.chooongg.core.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.chooongg.core.ext.inflateBinding

abstract class BaseActivity<out BINDING : ViewBinding> : AppCompatActivity() {

    @Suppress("UNCHECKED_CAST")
    protected val binding: BINDING by lazy { inflateBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}