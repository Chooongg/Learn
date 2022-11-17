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
import com.chooongg.form.provider.FormButtonProvider
import com.chooongg.form.provider.FormDividerProvider
import com.chooongg.form.provider.FormTextProvider
import com.chooongg.form.style.FormStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.lang.ref.WeakReference

class FormAdapter internal constructor(private val manager: FormManager, val style: FormStyle) :
    RecyclerView.Adapter<FormViewHolder>() {

    private var _recyclerView: WeakReference<RecyclerView>? = null

    val recyclerView get() = _recyclerView?.get()

    private var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val itemProviders by lazy(LazyThreadSafetyMode.NONE) { SparseArray<BaseFormProvider<out BaseForm>>() }

    internal var data: MutableList<ArrayList<BaseForm>> = arrayListOf()

    private val asyncDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<BaseForm>() {
        override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm): Boolean {
            return oldItem == newItem
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
        addItemProvider(FormButtonProvider(manager))
        addItemProvider(FormDividerProvider(manager))
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
        val partIndex = manager.adapter.adapters.indexOf(this)
        val partIsFirst = partIndex <= 0
        val partIsLast = partIndex >= manager.adapter.adapters.lastIndex
        val list = ArrayList<BaseForm>()
        data.forEachIndexed { groupIndex, group ->
            var index = 0
            group.forEach {
                if (hasGroupName) {
                    index++
                }
                if (it.isRealVisible(manager)) {
                    it.adapterPosition = index
                    it.adapterTopBoundary = if (index == 0) {
                        if (groupIndex == 0 && partIsFirst) {
                            FormBoundaryType.GLOBAL
                        } else FormBoundaryType.LOCAL
                    } else FormBoundaryType.NONE
                    list.add(it)
                    index++
                }
            }
        }
        list@ for (index in list.lastIndex downTo 0) {
            val item = list[index]
            if (index == list.lastIndex) {
                item.adapterBottomBoundary =
                    if (partIsLast) FormBoundaryType.GLOBAL else FormBoundaryType.LOCAL
                continue@list
            }
            val nextItem = list[index + 1]
            item.adapterBottomBoundary = if (nextItem.adapterPosition == 0) FormBoundaryType.LOCAL
            else FormBoundaryType.NONE
        }
        asyncDiffer.submitList(list) {
            notifyItemRangeChanged(0, list.size, "update")
        }
    }

    fun getItem(position: Int) = asyncDiffer.currentList[position]

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
    override fun onBindViewHolder(
        holder: FormViewHolder, position: Int
    ) = getItemProvider(holder.itemViewType)!!
        .onBindViewHolder(holder, position)

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(
        holder: FormViewHolder, position: Int, payloads: MutableList<Any>
    ) = getItemProvider(holder.itemViewType)!!
        .onBindViewHolder(holder, position, payloads)

    override fun getItemViewType(position: Int) =
        if (manager.isEditable) getItem(position).type + style.typeIncrement else getItem(position).seeType + style.typeIncrement

    private fun getItemProvider(viewType: Int): BaseFormProvider<out BaseForm>? =
        itemProviders.get(viewType - style.typeIncrement)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this._recyclerView = null
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }
}