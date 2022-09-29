package com.chooongg.learn.popupMenu

import android.os.Bundle
import android.widget.Spinner
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.core.popupMenu.popupMenu
import com.chooongg.learn.R
import com.chooongg.learn.databinding.ActivityPopupMenuBinding

class PopupMenuActivity : BasicBindingActivity<ActivityPopupMenuBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnDefault.doOnClick {
            popupMenu {
                section {
                    title = "测试标题"
                    item {
                        label = "测试选项1"
                    }
                    item {
                        label = "测试选项2"
                        icon = R.drawable.ic_main_file_picker
                    }
                }
            }.show(context, it)
        }
        binding.btnFullWidth.doOnClick {
            popupMenu {
                width = it.width
                section {
                    title = "测试标题"
                    item {
                        label = "测试选项1"
                    }
                    item {
                        label = "测试选项2"
                        icon = R.drawable.ic_launcher_foreground
                    }
                }
            }.show(context, it)
        }
        binding.btnOverlapAnchor.doOnClick {
            popupMenu {
                width = it.width
                overlapAnchor = true
                section {
                    title = "测试标题"
                    item {
                        label = "测试选项1"
                    }
                    item {
                        label = "测试选项2"
                        icon = R.drawable.ic_main_file_picker
                    }
                }
            }.show(context, it)
        }
    }
}