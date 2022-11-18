package com.chooongg.learn.form

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.chooongg.basic.ext.setNightMode
import com.chooongg.core.activity.BasicBindingModelActivity
import com.chooongg.core.popupMenu.popupMenu
import com.chooongg.form.FormEventListener
import com.chooongg.form.FormManager
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.enum.FormVisibilityMode
import com.chooongg.learn.databinding.ActivityFormBinding

class FormActivity : BasicBindingModelActivity<ActivityFormBinding, FormActivity.FormModel>(),
    FormEventListener {

    class FormModel : ViewModel() {
        val formManager = FormManager(true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        model.formManager.apply {
            attach(this@FormActivity, binding.recyclerView, this@FormActivity)
            if (getItemCount() <= 0) {
                addGroup {
                    setNewList {
                        addPart {
                            addButton("可编辑", "isEditable")
                            addButton("夜间模式", "nightMode")
                        }
                    }
                }
                addCardGroup {
                    setNewList {
                        addPart {
                            addText("仅查看", "only_see") {
                                content = "此项仅查看时显示"
                                visibilityMode = FormVisibilityMode.ONLY_SEE
                            }
                            addDivider() {
                                isOnEdgeVisible = true
                            }
                            addText("仅编辑", "only_edit") {
                                content = "此项仅编辑时显示"
                                visibilityMode = FormVisibilityMode.ONLY_EDIT
                            }
                            addText("文本", "text") {
                                content = "这是一个文本"
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onFormClick(manager: FormManager, item: BaseForm, view: View, position: Int) {
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
        }
    }
}