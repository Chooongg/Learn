package com.chooongg.form.provider

import androidx.core.view.updatePaddingRelative
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.doOnClick
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.showToast
import com.chooongg.core.popupMenu.popupMenu
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormSelect
import com.chooongg.form.enum.FormOptionsLoadScene
import com.chooongg.form.loader.OptionsLoadResult
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class FormSelectProvider(manager: FormManager) : BaseFormProvider<FormSelect>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_SELECT
    override val layoutId: Int get() = R.layout.form_item_select
    override fun onBindViewHolder(holder: FormViewHolder, item: FormSelect) {
        with(holder.getView<MaterialButton>(R.id.form_btn_content)) {
            val verticalPadding = resDimensionPixelSize(R.dimen.formItemVertical)
            updatePaddingRelative(
                top = verticalPadding,
                bottom = verticalPadding
            )
            isEnabled = item.isRealEnable(manager)
            text = item.transformContent(context)
            hint = item.hint ?: context.getString(R.string.form_select_hint)
            if (item.isNeedLoadOptions(FormOptionsLoadScene.BIND)) {
                item.getOptionsLoader()!!.let {
                    groupAdapter?.adapterScope?.launch {
                        val result = it.loadOptions(item)
                        if (result is OptionsLoadResult.Success) {
                            text = item.transformContent(context)
                        } else if (result is OptionsLoadResult.Error && result.throwable !is CancellationException) {
                            showToast(result.throwable.message)
                        }
                    }
                }
            }
            doOnClick {
                if (item.isNeedLoadOptions(FormOptionsLoadScene.BIND)) {
                    item.getOptionsLoader()!!.let {
                        groupAdapter?.adapterScope?.launch {
                            val result = it.loadOptions(item)
                            if (result is OptionsLoadResult.Success) {
                                showPopup(holder, this@with, item)
                            } else if (result is OptionsLoadResult.Error && result.throwable !is CancellationException) {
                                showToast(result.throwable.message)
                            }
                        }
                    }
                } else showPopup(holder, this@with, item)
            }
        }
    }

    private fun showPopup(holder: FormViewHolder, anchor: MaterialButton, item: FormSelect) {
        if (item.options.isNullOrEmpty()) {
            showToast("暂无可选项")
            return
        }
        popupMenu {
            overlapAnchor = true
            dropDownWidth = anchor.width
            section {
                title = item.selectorTitle
                item.options?.forEach {
                    item {
                        label = it.getValue()
                        if (item.content == it.getKey()) {
                            labelColor = anchor.attrColor(androidx.appcompat.R.attr.colorPrimary)
                        }
                        onSelectedCallback {
                            item.content = it.getKey()
                            anchor.text = item.transformContent(context)
                            groupAdapter?.onFormContentChanged(
                                manager, item, holder.absoluteAdapterPosition
                            )
                        }
                    }
                }
            }
        }.show(anchor.context, anchor)
    }
}