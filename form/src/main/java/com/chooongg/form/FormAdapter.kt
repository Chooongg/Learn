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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class FormAdapter internal constructor(private val manager: FormManager, style: Int) :
    RecyclerView.Adapter<FormViewHolder>() {

    private val adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val itemProviders by lazy(LazyThreadSafetyMode.NONE) { SparseArray<BaseFormProvider<out BaseForm>>() }

    private var data: MutableList<ArrayList<BaseForm>> = arrayListOf()

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

    fun addItemProvider(provider: BaseFormProvider<out BaseForm>) {
        provider.setAdapter(this)
        itemProviders.put(provider.itemViewType, provider)
    }

    fun setNewList(list: ArrayList<BaseForm>) {
        data.clear()
        data.add(list)
        update()
    }

    fun setNewList(list: MutableList<ArrayList<BaseForm>>) {
        data = list
        update()
    }

    fun update() {
        val list = ArrayList<BaseForm>()
        data.forEach { group -> group.forEach { if (it.isRealVisible(manager)) list.add(it) } }
        asyncDiffer.submitList(list)
    }

    fun getItem(position: Int) = asyncDiffer.currentList[position]

    fun getBoundary(position: Int) {
        val adapterIndex = manager.adapter.adapters.indexOf(this)
        var index = 0
        var top = FormBoundaryType.NONE
        var bottom = FormBoundaryType.NONE
        // 顶部边界
        var hasLocalBoundary = false
        for (group in data) {

        }
        // 底部边界
    }

    override fun getItemCount(): Int {
        var count = 0
        data.forEach { group -> group.forEach { if (it.isRealVisible(manager)) count++ } }
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

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapterScope.cancel()
    }
}