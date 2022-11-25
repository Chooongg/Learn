package com.chooongg.form.provider

import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.chooongg.basic.ext.multipleValid
import com.chooongg.basic.ext.resDimensionPixelSize
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
            suffixText = item.suffixText
            prefixText = item.prefixText
            endIconMinSize =
                resDimensionPixelSize(R.dimen.formItemMenuIconSize) + resDimensionPixelSize(R.dimen.formItemMenuIconPadding) * 2
            if (item.menuIcon != null) {
                endIconMode = TextInputLayout.END_ICON_CUSTOM
                setEndIconDrawable(item.menuIcon!!)
                setEndIconOnClickListener {
                    if (multipleValid()) {
                        recyclerView?.clearFocus()
                        adapter?.onFormMenuClick(
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
            isEnabled = item.isEnabled
            hint = item.hint ?: context.getString(R.string.form_input_hint)
            setText(item.transformContent())
            val watcher = doAfterTextChanged {
                item.content = it?.toString()
                adapter?.onFormContentChanged(manager, item, holder.absoluteAdapterPosition)
            }
            tag = watcher
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    recyclerView?.clearFocus()
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }
}