package com.chooongg.learn.form

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DefaultItemAnimator
import com.chooongg.basic.ext.logE
import com.chooongg.basic.ext.setNightMode
import com.chooongg.core.activity.BasicBindingModelActivity
import com.chooongg.core.popupMenu.popupMenu
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormEventListener
import com.chooongg.form.bean.BaseForm
import com.chooongg.learn.databinding.ActivityFormBinding
import com.chooongg.learn.form.viewModel.FormViewModel

class FormActivity : BasicBindingModelActivity<ActivityFormBinding, FormViewModel>(),
    FormEventListener {

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView.itemAnimator = DefaultItemAnimator().apply {
        }
        model.formManager.init(binding.recyclerView, this)
    }

    override fun onFormClick(manager: BaseFormManager, item: BaseForm, view: View, position: Int) {
        when (item.field) {
            "isEditable" -> {
                item.name = if (manager.isEditable) "不可编辑" else "可编辑"
                manager.isEditable = !manager.isEditable
            }
            "nightMode" -> {
                popupMenu {
                    overlapAnchor = true
                    dropDownWidth = view.width
                    section {
                        item {
                            label = "日间模式"
                            onSelectedCallback { setNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
                        }
                        item {
                            label = "夜间模式"
                            onSelectedCallback { setNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
                        }
                    }
                }.show(context, view)
            }
            "submit" -> {
                if (manager.checkDataCorrectness()) {
                    val json = manager.output()
                    logE("Form", json.toString(4))
                }
            }
        }
    }

    override fun onFormMenuClick(
        manager: BaseFormManager,
        item: BaseForm,
        view: View,
        position: Int
    ) {

    }
}