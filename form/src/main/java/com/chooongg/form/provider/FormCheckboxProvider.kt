package com.chooongg.form.provider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.withMain
import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormCheckbox
import com.chooongg.form.bean.Option
import com.chooongg.form.enum.FormOptionsLoadScene
import com.chooongg.form.enum.FormOptionsLoadState
import com.google.android.material.checkbox.MaterialCheckBox
import kotlinx.coroutines.launch

class FormCheckboxProvider(manager: FormManager) : BaseFormProvider<FormCheckbox>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_CHECKBOX
    override val layoutId: Int get() = R.layout.form_item_checkbox

    override fun onBindViewHolder(
        holder: FormViewHolder, item: FormCheckbox
    ) {
        with(holder.getView<RecyclerView>(R.id.form_recycler_view)) {
            val childAdapter = ChildAdapter(manager, groupAdapter, item)
            adapter = childAdapter
            if (item.isNeedLoadOptions(FormOptionsLoadScene.BIND)) {
                item.getOptionsLoader()!!.let {
                    groupAdapter?.adapterScope?.launch {
                        it.loadOptions(item)
                        if (it.state == FormOptionsLoadState.WAIT) {
                            withMain { childAdapter.update() }
                        }
                    }
                }
            }
        }
    }

    private class ChildAdapter(
        val manager: FormManager,
        val adapter: FormGroupAdapter?,
        val form: FormCheckbox
    ) :
        RecyclerView.Adapter<FormViewHolder>() {

        val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Option>() {
            override fun areItemsTheSame(oldItem: Option, newItem: Option): Boolean {
                return oldItem.getKey() == newItem.getKey()
            }

            override fun areContentsTheSame(oldItem: Option, newItem: Option): Boolean {
                return oldItem.getKey() == newItem.getKey()
                        && oldItem.getValue() == newItem.getValue()
            }
        })

        init {
            update()
        }

        fun update() {
            val list = ArrayList<Option>()
            form.options?.forEach { if (it.isShow()) list.add(it) }
            differ.submitList(list)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FormViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.form_item_checkbox_child, parent, false)
            )

        override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
            val item = differ.currentList[position]
            with(holder.getView<MaterialCheckBox>(R.id.form_checkbox)) {
                setOnCheckedChangeListener(null)
                text = item.getValue()
                isChecked = form.selectedKey.contains(item.getKey())
                isEnabled = manager.isEditable && form.isEnabled
                setOnCheckedChangeListener { _, isChecked ->
                    adapter?.clearFocus()
                    if (isChecked) {
                        form.selectedKey.add(item.getKey())
                    } else {
                        form.selectedKey.remove(item.getKey())
                    }
                    adapter?.onFormContentChanged(manager, form, holder.absoluteAdapterPosition)
                }
            }
        }

        override fun getItemCount() = differ.currentList.size
    }
}