package com.chooongg.form.provider

import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import com.chooongg.basic.ext.multipleValid
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormInput
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormInputProvider(manager: FormManager) : BaseFormProvider<FormInput>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_INPUT
    override val layoutId: Int get() = R.layout.form_item_input
    override fun onBindViewHolder(holder: FormViewHolder, item: FormInput) {
        with(holder.getView<TextInputLayout>(R.id.form_input_content)) {
            isEnabled = item.isEnabled
            prefixText = item.prefixText
            suffixText = item.suffixText
            if (item.menuIcon != null) {
                endIconMode = TextInputLayout.END_ICON_CUSTOM
                setEndIconDrawable(item.menuIcon!!)
                setEndIconOnClickListener {
                    if (multipleValid()) {
                        groupAdapter?.clearFocus()
                        groupAdapter?.onFormMenuClick(
                            manager, item, this, holder.absoluteAdapterPosition
                        )
                    }
                }
            } else {
                endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                setEndIconDrawable(R.drawable.form_ic_edit_clear)
            }
        }
        with(holder.getView<TextInputEditText>(R.id.form_edit_content)) {
            if (tag is TextWatcher) removeTextChangedListener(tag as TextWatcher)
            hint = item.hint ?: context.getString(R.string.form_input_hint)
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
}