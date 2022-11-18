package com.chooongg.form

import android.content.res.ColorStateList
import android.util.SparseArray
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.*
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.enum.FormColorStyle
import com.chooongg.form.provider.*
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

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val itemProviders by lazy(LazyThreadSafetyMode.NONE) { SparseArray<BaseFormProvider<out BaseForm>>() }

    internal var data: MutableList<ArrayList<BaseForm>> = arrayListOf()

    private val asyncDiffer = AsyncListDiffer(object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit

        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onRemoved(position: Int, count: Int) =
            notifyItemRangeRemoved(position, count)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)
    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseForm>() {
        override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) =
            oldItem.type == newItem.type && oldItem.name == newItem.name && oldItem.field == newItem.field

        override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) = oldItem == newItem
    }).build())

    var name: CharSequence? = null

    var nameColorStyle: FormColorStyle = FormColorStyle.DEFAULT

    var field: String? = null

    @DrawableRes
    var icon: Int? = null

    var iconTint: ColorStateList? = null

    val hasGroupTitle get() = name != null

    var formEventListener: FormEventListener? = null
        internal set

    init {
        // 必备组件
        addItemProvider(FormGroupTitleProvider(manager))
        addItemProvider(FormTextProvider(manager))

        addItemProvider(FormButtonProvider(manager))
        addItemProvider(FormDividerProvider(manager))
    }

    fun addItemProvider(provider: BaseFormProvider<out BaseForm>) {
        provider.setAdapter(this)
        itemProviders.put(provider.itemViewType, provider)
    }

    fun setNewList(block: FormCreateGroup.() -> Unit) {
        setNewList(FormCreateGroup().apply(block))
    }

    fun setNewList(createGroup: FormCreateGroup) {
        name = createGroup.groupName
        nameColorStyle = createGroup.groupNameColorStyle
        field = createGroup.groupField
        icon = createGroup.groupIcon
        iconTint = createGroup.groupIconTint
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        data = createGroup.createdFormPartList
        data.forEach { it.forEach { item -> item.configData() } }
        update()
    }

    fun update() {
        val groupIndex = manager.adapter.adapters.indexOf(this)
        val groupIsFirst = groupIndex <= 0
        val groupIsLast = groupIndex >= manager.adapter.adapters.lastIndex
        val list = ArrayList<BaseForm>()
        data.forEachIndexed { partIndex, part ->
            var index = 0
            if (hasGroupTitle) {
                list.add(FormGroupTitle(name!!, field).apply {
                    icon = this@FormAdapter.icon
                    iconTint = this@FormAdapter.iconTint
                    nameColorStyle = this@FormAdapter.nameColorStyle
                    adapterPosition = 0
                    adapterTopBoundary = if (partIndex == 0 && groupIsFirst) {
                        FormBoundaryType.GLOBAL
                    } else FormBoundaryType.LOCAL
                })
                index++
            }
            part@ for (j in 0 until part.size) {
                val item = part[j]
                if (!item.isRealVisible(manager)) continue@part

                item.adapterPosition = index
                item.adapterTopBoundary = if (index == 0) {
                    if (partIndex == 0 && groupIsFirst) {
                        FormBoundaryType.GLOBAL
                    } else FormBoundaryType.LOCAL
                } else FormBoundaryType.NONE
                list.add(item)
                index++
            }
        }
        list@ for (index in list.lastIndex downTo 0) {
            val item = list[index]
            if (index == list.lastIndex) {
                item.adapterBottomBoundary =
                    if (groupIsLast) FormBoundaryType.GLOBAL else FormBoundaryType.LOCAL
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

    override fun getItemCount() = asyncDiffer.currentList.size

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