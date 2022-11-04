package com.chooongg.form

import android.content.res.ColorStateList
import android.util.SparseArray
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.enum.FormColorStyle
import com.chooongg.form.provider.BaseFormProvider
import com.chooongg.form.provider.FormTextProvider
import com.chooongg.form.style.FormBoundary
import com.chooongg.form.style.FormStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class FormAdapter internal constructor(private val manager: FormManager, val style: FormStyle) :
    RecyclerView.Adapter<FormViewHolder>() {

    private var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val itemProviders by lazy(LazyThreadSafetyMode.NONE) { SparseArray<BaseFormProvider<out BaseForm>>() }

    internal var data: MutableList<ArrayList<BaseForm>> = arrayListOf()

    private val asyncDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<BaseForm>() {
        override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm): Boolean {
            return oldItem.type == newItem.type && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm): Boolean {
            return oldItem == newItem
        }
    })

    var groupName: CharSequence? = null

    var groupNameColorStyle: FormColorStyle = FormColorStyle.DEFAULT

    var groupField: String? = null

    @DrawableRes
    var groupIcon: Int? = null

    var groupIconTint: ColorStateList? = null

    val hasGroupName get() = groupName != null

    init {
        addItemProvider(FormTextProvider(manager))
    }

    fun addItemProvider(provider: BaseFormProvider<out BaseForm>) {
        provider.setAdapter(this)
        itemProviders.put(provider.itemViewType, provider)
    }

    fun setNewList(list: ArrayList<BaseForm>) {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        data.clear()
        data.add(list)
        update()
    }

    fun setNewList(list: MutableList<ArrayList<BaseForm>>) {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        data = list
        update()
    }

    fun update() {
        val list = ArrayList<BaseForm>()
        data.forEach { group ->
            group.forEach {
                if (hasGroupName) {

                }
                if (it.isRealVisible(manager)) list.add(it)
            }
        }
        asyncDiffer.submitList(list)
    }

    fun getItem(position: Int) = asyncDiffer.currentList[position]

    fun getBoundary(position: Int): FormBoundary {
        val adapterIndex = manager.adapter.adapters.indexOf(this)
        val adapterIsLast = adapterIndex >= manager.adapter.adapters.lastIndex
        var index = 0
        var top = FormBoundaryType.NONE
        var bottom = FormBoundaryType.NONE
        // 顶部边界
        val adapterIsFirst = adapterIndex <= 0
        group@ for (i in 0 until data.size) {
            if (hasGroupName) {
                if (index == position) {
                    top = if (adapterIsFirst && i == 0) {
                        FormBoundaryType.GLOBAL
                    } else FormBoundaryType.LOCAL
                    break@group
                }
                index++
            }
            val group = data[i]
            if (index + group.size <= position) {
                for (j in 0 until group.size) {
                    if (index == position) {

                        break@group
                    }
                    index++
                }
            } else index += group.size
        }

        // 底部边界

        return FormBoundary(top, bottom)
    }

    override fun getItemCount(): Int {
        var count = 0
        data.forEach { group ->
            group.forEach {
                if (hasGroupName) count++
                if (it.isRealVisible(manager)) count++
            }
        }
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {
        val provider = getItemProvider(viewType)
        checkNotNull(provider) { "ViewType: $viewType 未找到Provider，请先调用 addItemProvider() 添加Provider!" }
        provider.context = parent.context
        return provider.onCreateViewHolder(parent, viewType).also {
            provider.onViewHolderCreated(it)
        }
    }

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {
        getItemProvider(holder.itemViewType)!!.onBindViewHolder(holder, position)
    }

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) = getItemProvider(holder.itemViewType)!!.onBindViewHolder(holder, position, payloads)

    override fun getItemViewType(position: Int) =
        if (manager.isEditable) getItem(position).type else getItem(position).seeOnlyType

    private fun getItemProvider(viewType: Int): BaseFormProvider<out BaseForm>? =
        itemProviders.get(viewType)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        style.context = recyclerView.context
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }
}