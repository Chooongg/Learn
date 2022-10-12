package com.chooongg.mediaPicker

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.chooongg.basic.ext.attrBoolean
import com.chooongg.basic.ext.doOnClick
import com.chooongg.basic.ext.withMain
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.mediaPicker.databinding.LearnActivityMediaPickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LearnMediaPickerActivity : BasicBindingActivity<LearnActivityMediaPickerBinding>() {

//    private lateinit var selectedMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!attrBoolean(com.google.android.material.R.attr.isMaterial3Theme, false)) {
            setTheme(com.chooongg.core.R.style.Theme_Learn_Surface)
        }
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        title = "选择图片"
//        selectedMenuItem = binding.bottomAppBar.menu.add(0, 0, 0, "已选择").apply {
//            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
//        }
        lifecycleScope.launch {
            delay(2000)
            withMain {
                binding.fabSelect.show()
            }
        }
        binding.btnAlbum.doOnClick {
            val dialog = BottomSheetDialog(context)
        }
    }
}