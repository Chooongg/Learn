package com.chooongg.form.provider

import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.chooongg.basic.ext.multipleValid
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormInput
import com.chooongg.form.bean.FormInputAutoComplete
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class FormInputAutoCompleteProvider(manager: FormManager) : BaseFormProvider<FormInputAutoComplete>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_INPUT_AUTO_COMPLETE
    override val layoutId: Int get() = R.layout.form_item_input_auto_complete
    override fun onBindViewHolder(holder: FormViewHolder, item: FormInputAutoComplete) {
        with(holder.getView<TextInputLayout>(R.id.form_input_content)) {
            isEnabled = item.isEnabled
            prefixText = item.prefixText
            suffixText = item.suffixText
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
            hint = item.hint ?: context.getString(R.string.form_input_hint)
            text
            setText(item.content)
            val watcher = doAfterTextChanged {
                item.content = if (it.isNullOrEmpty()) null else it
                adapter?.onFormContentChanged(manager, item, holder.absoluteAdapterPosition)
            }
            tag = watcher
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    recyclerView?.clearFocus()
                    true
                } else false
            }
        }
    }
}