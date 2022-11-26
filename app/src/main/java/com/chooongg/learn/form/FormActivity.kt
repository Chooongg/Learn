package com.chooongg.learn.form

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.setNightMode
import com.chooongg.core.activity.BasicBindingModelActivity
import com.chooongg.core.popupMenu.popupMenu
import com.chooongg.core.widget.TopAppBarLayout
import com.chooongg.form.FormEventListener
import com.chooongg.form.FormManager
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.enum.FormVisibilityMode
import com.chooongg.learn.R
import com.chooongg.learn.databinding.ActivityFormBinding

class FormActivity : BasicBindingModelActivity<ActivityFormBinding, FormActivity.FormModel>(),
    FormEventListener {

    class FormModel : ViewModel() {
        val formManager = FormManager(true, 4)
    }

    override fun initView(savedInstanceState: Bundle?) {

        model.formManager.apply {
            init(this@FormActivity, binding.recyclerView, this@FormActivity)
            if (getItemCount() <= 0) {
                addGroup {
                    groupName = "选项菜单"
                    groupIcon = R.drawable.ic_main_popup_menu
                    groupIconTint =
                        ColorStateList.valueOf(attrColor(com.google.android.material.R.attr.colorOnSurface))
                    addPart {
                        addButton("可编辑", "isEditable")
                        addButton("夜间模式", "nightMode")
                    }
                }
                addCardGroup {
                    groupName = "Form表单"
                    groupIcon = R.drawable.ic_main_form
                    groupIconTint =
                        ColorStateList.valueOf(attrColor(com.google.android.material.R.attr.colorOnSecondaryContainer))
                    addPart {
                        addText("仅查看", "only_see") {
                            content = "仅查看时显示的文本"
                            visibilityMode = FormVisibilityMode.ONLY_SEE
                        }
                        addText("仅编辑", "only_edit") {
                            content = "仅编辑时显示的文本"
                            visibilityMode = FormVisibilityMode.ONLY_EDIT
                        }
                        addText("文本", "text") {
                            content = "基本文本"
                        }
                        addText("文本", "text_menu") {
                            content = "带菜单按钮的文本"
                            menuIcon = R.drawable.ic_night_mode_day
                            menuIconTint =
                                ColorStateList.valueOf(attrColor(com.google.android.material.R.attr.colorSecondary))
                        }
                        addDivider()
                        addAddress("地址选择", "address") {

                        }
                        addInput("输入框", "edit") {
                            prefixText = "￥"
                            suffixText = "米"
                        }
                    }
                }
                addCardGroup {
                    groupName = "动态表单"
                    dynamicGroup = true
                    dynamicMaxPartCount = 4
                    addPart {
                        addText("文本", "text") {
                            content = "基本文本"
                        }
                        addText("文本", "text") {
                            content = "基本文本"
                        }
                        addInput("输入框", "input")
                    }
                    dynamicGroupAddPartListener {
                        addText("文本", "text") {
                            content = "基本文本"
                        }
                        addText("文本", "text") {
                            content = "基本文本"
                        }
                        addInput("输入框", "input")
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
            else -> binding.recyclerView.smoothScrollToPosition(0)
        }
    }
}