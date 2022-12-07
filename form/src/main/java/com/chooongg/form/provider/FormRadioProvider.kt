package com.chooongg.form.provider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.showToast
import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormRadio
import com.chooongg.form.bean.Option
import com.chooongg.form.enum.FormOptionsLoadScene
import com.chooongg.form.loader.OptionsLoadResult
import com.google.android.material.radiobutton.MaterialRadioButton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class FormRadioProvider(manager: FormManager) : BaseFormProvider<FormRadio>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_RADIO
    override val layoutId: Int get() = R.layout.form_item_radio

    override fun onBindViewHolder(
        holder: FormViewHolder, item: FormRadio
    ) {
        with(holder.getView<RecyclerView>(R.id.form_recycler_view)) {
            val childAdapter = ChildAdapter(manager, groupAdapter, item)
            adapter = childAdapter
            if (item.isNeedLoadOptions(FormOptionsLoadScene.BIND)) {
                item.getOptionsLoader()!!.let {
                    groupAdapter?.adapterScope?.launch {
                        val result = it.loadOptions(item)
                        if (result is OptionsLoadResult.Success) {
                            groupAdapter?.notifyItemChanged(holder.bindingAdapterPosition, "update")
                        } else if (result is OptionsLoadResult.Error && result.throwable !is CancellationException) {
                            showToast(result.throwable.message)
                        }
                    }
                }
            }
        }
    }

    private class ChildAdapter(
        val manager: FormManager,
        val adapter: FormGroupAdapter?,
        val form: FormRadio
    ) : RecyclerView.Adapter<FormViewHolder>() {

        private var recyclerView: RecyclerView? = null

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
                    .inflate(R.layout.form_item_radio_child, parent, false)
            )

        override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
            val item = differ.currentList[position]
            with(holder.getView<MaterialRadioButton>(R.id.form_radio)) {
                setOnCheckedChangeListener(null)
                text = item.getValue()
                isChecked = form.content == item.getKey()
                isEnabled = manager.isEditable && form.isEnabled
                setOnCheckedChangeListener { _, isChecked ->
                    adapter?.clearFocus()
                    if (isChecked) {
                        form.content = item.getKey()
                        adapter?.onFormContentChanged(manager, form, holder.absoluteAdapterPosition)
                        for (i in 0 until itemCount) {
                            if (i != holder.bindingAdapterPosition) notifyItemChanged(i, "update")
                        }
                    }
                }
            }
        }

        override fun getItemCount() = differ.currentList.size

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            this.recyclerView = recyclerView
        }

        override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
            this.recyclerView = null
        }
    }
}