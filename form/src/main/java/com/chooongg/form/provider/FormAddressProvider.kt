package com.chooongg.form.provider

import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
import androidx.core.widget.doAfterTextChanged
import com.chooongg.basic.ext.gone
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.resString
import com.chooongg.basic.ext.visible
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormAddress
import com.chooongg.form.enum.FormAreaMode
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormAddressProvider(manager: BaseFormManager) : BaseFormProvider<FormAddress>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_ADDRESS
    override val layoutId: Int get() = R.layout.form_item_address
    override fun onBindViewHolder(holder: FormViewHolder, item: FormAddress) {
        val areaButton = holder.getView<MaterialButton>(R.id.form_btn_area)
        val inputContent = holder.getView<TextInputLayout>(R.id.form_input_content)
        val editContent = holder.getView<TextInputEditText>(R.id.form_edit_content)
        when (item.areaMode) {
            FormAreaMode.PROVINCE_CITY_AREA_ADDRESS -> {
                configAreaButton(item, areaButton)
                inputContent.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    topToBottom = R.id.form_btn_area
                }
            }
            FormAreaMode.AREA_ADDRESS -> {
                configAreaButton(item, areaButton)
                inputContent.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    topToBottom = R.id.form_btn_area
                }
            }
            FormAreaMode.ADDRESS -> {
                areaButton.gone()
                inputContent.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                }
            }
        }
        with(editContent) {
            if (tag is TextWatcher) removeTextChangedListener(tag as TextWatcher)
            isEnabled = item.isRealEnable(manager)
            hint = item.hint ?: resString(R.string.form_address_hint)
            setText(item.content)
            val watcher = doAfterTextChanged {
                item.content = if (it.isNullOrEmpty()) null else it
                groupAdapter?.onFormContentChanged(manager, item, holder.absoluteAdapterPosition)
            }
            tag = watcher
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    groupAdapter?.clearFocus()
                    true
                } else false
            }
        }
    }

    private fun configAreaButton(item: FormAddress, button: MaterialButton) {
        with(button) {
            val verticalPadding = resDimensionPixelSize(R.dimen.formItemVertical)
            updatePaddingRelative(
                top = verticalPadding,
                bottom = verticalPadding
            )
            isEnabled = item.isRealEnable(manager)
            hint = item.areaHint ?: resString(R.string.form_address_area_hint)
            visible()
        }
    }
}