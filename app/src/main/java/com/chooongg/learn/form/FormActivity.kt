package com.chooongg.learn.form

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.chooongg.basic.ext.logE
import com.chooongg.basic.ext.setNightMode
import com.chooongg.core.activity.BasicBindingModelActivity
import com.chooongg.core.popupMenu.popupMenu
import com.chooongg.form.FormManager
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormButton
import com.chooongg.form.bean.FormText
import com.chooongg.form.enum.FormVisibilityMode
import com.chooongg.form.style.CardFormStyle
import com.chooongg.learn.databinding.ActivityFormBinding

class FormActivity : BasicBindingModelActivity<ActivityFormBinding, FormActivity.FormModel>() {

    class FormModel : ViewModel() {
        val formManager = FormManager(true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        model.formManager.attach(this, binding.recyclerView)
        logE("Context", context)
        if (model.formManager.getItemCount() <= 0) {
            model.formManager.apply {
                addPart(CardFormStyle()) {
                    setNewList(ArrayList<BaseForm>().apply {
                        add(FormText("仅查看").apply {
                            field = "test"
                            content = "仅查看内容"
                            visibilityMode = FormVisibilityMode.ONLY_SEE
                        })
                        add(FormText("测试").apply {
                            field = "test"
                            content = "测试内容"
                        })
                        add(FormText("仅编辑").apply {
                            field = "test"
                            content = "仅编辑内容"
                            visibilityMode = FormVisibilityMode.ONLY_EDIT
                        })
                        add(FormButton("不可编辑").apply {
                            field = "isEditable"
                            doOnClick {
                                name = if (isEditable) "可编辑" else "不可编辑"
                                isEditable = !isEditable
                            }
                        })
                    })
                }
                addPart {
                    setNewList(ArrayList<BaseForm>().apply {
                        add(FormButton("夜间模式").apply {
                            doOnClick {
                                popupMenu {
                                    overlapAnchor = true
                                    dropDownWidth = it.width
                                    section {
                                        title = "夜间模式选择"
                                        item {
                                            label = "日间模式"
                                            onSelectedCallback {
                                                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                            }
                                        }
                                        item {
                                            label = "夜间模式"
                                            onSelectedCallback {
                                                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                            }
                                        }
                                    }
                                }.show(context, it)
                                logE("ContextClick", context)
                            }
                        })
                    })
                }
            }
        }
    }
}