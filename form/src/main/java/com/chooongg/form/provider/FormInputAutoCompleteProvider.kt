package com.chooongg.form.provider

import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import com.chooongg.basic.ext.multipleValid
import com.chooongg.basic.ext.showToast
import com.chooongg.basic.ext.withMain
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormInputAutoComplete
import com.chooongg.form.enum.FormOptionsLoadScene
import com.chooongg.form.enum.FormOptionsLoadState
import com.chooongg.form.loader.OptionsLoadResult
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class FormInputAutoCompleteProvider(manager: FormManager) :
    BaseFormProvider<FormInputAutoComplete>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_INPUT_AUTO_COMPLETE
    override val layoutId: Int get() = R.layout.form_item_input_auto_complete
    override fun onBindViewHolder(holder: FormViewHolder, item: FormInputAutoComplete) {
        groupAdapter
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
                endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
                setEndIconDrawable(R.drawable.form_ic_dropdown)
            }
        }
        with(holder.getView<MaterialAutoCompleteTextView>(R.id.form_edit_content)) {
            if (item.isNeedLoadOptions(FormOptionsLoadScene.BIND)) {
                item.getOptionsLoader()!!.let {
                    groupAdapter?.adapterScope?.launch {
                        val result = it.loadOptions(item)
                        if (result is OptionsLoadResult.Success) {
                            setSimpleItems(Array(item.options?.size ?: 0) {
                                item.options!![it].getValue().toString()
                            })
                        } else if (result is OptionsLoadResult.Error && result.throwable !is CancellationException) {
                            showToast(result.throwable.message)
                        }
                    }
                }
            }
            if (tag is TextWatcher) removeTextChangedListener(tag as TextWatcher)
            hint = item.hint ?: context.getString(R.string.form_input_hint)
            setText(item.content)
            setSimpleItems(Array(item.options?.size ?: 0) {
                item.options!![it].getValue().toString()
            })
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
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    if (item.isNeedLoadOptions(FormOptionsLoadScene.TRIGGER)) {
                        item.getOptionsLoader()!!.let {
                            groupAdapter?.adapterScope?.launch {
                                it.loadOptions(item)
                                if (it.state == FormOptionsLoadState.WAIT) {
                                    withMain {
                                        setSimpleItems(Array(item.options?.size ?: 0) {
                                            item.options!![it].getValue().toString()
                                        })
                                        if (isFocused) showDropDown()
                                    }
                                }
                            }
                        }
                    } else showDropDown()
                }
            }
        }
    }
}