package com.chooongg.mediaPicker

import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.lifecycleScope
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.mediaPicker.databinding.LearnActivityMediaPickerBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LearnMediaPickerActivity : BasicBindingActivity<LearnActivityMediaPickerBinding>() {

//    private lateinit var selectedMenuItem: MenuItem

    override fun initView(savedInstanceState: Bundle?) {
        title = "选择图片"
//        selectedMenuItem = binding.bottomAppBar.menu.add(0, 0, 0, "已选择").apply {
//            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
//        }
        lifecycleScope.launch {
            delay(2000)
            binding.fabSelect.show()
        }
        binding.btnAlbum.doOnClick {
            val dialog = BottomSheetDialog(context)
        }
    }

    override fun onBackPressed() {
        clearTransition()
        super.onBackPressed()
    }
}