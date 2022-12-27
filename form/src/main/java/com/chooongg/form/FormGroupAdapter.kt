package com.chooongg.form

import android.content.Context
import android.content.res.ColorStateList
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.*
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.hideIME
import com.chooongg.core.popupMenu.popupMenu
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.enum.FormGroupTitleMode
import com.chooongg.form.provider.*
import com.chooongg.form.style.FormStyle
import kotlinx.coroutines.*
import org.json.JSONObject
import java.lang.ref.WeakReference

class FormGroupAdapter internal constructor(
    private val manager: BaseFormManager, val style: FormStyle, private val typeIncrement: Int
) : RecyclerView.Adapter<FormViewHolder>() {

    private var _recyclerView: WeakReference<RecyclerView>? = null

    val recyclerView get() = _recyclerView?.get()

    var adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val itemProviders by lazy(LazyThreadSafetyMode.NONE) { SparseArray<BaseFormProvider<out BaseForm>>() }

    internal var data: MutableList<ArrayList<BaseForm>> = arrayListOf()

    private val asyncDiffer = AsyncListDiffer(object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit

        override fun onInserted(position: Int, count: Int) =
            notifyItemRangeInserted(position, count)

        override fun onRemoved(position: Int, count: Int) = notifyItemRangeRemoved(position, count)

        override fun onMoved(fromPosition: Int, toPosition: Int) =
            notifyItemMoved(fromPosition, toPosition)
    }, AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<BaseForm>() {
        override fun areItemsTheSame(oldItem: BaseForm, newItem: BaseForm) =
            if (oldItem.type != FormManager.TYPE_GROUP_NAME || newItem.type != FormManager.TYPE_GROUP_NAME) {
                oldItem.antiRepeatCode == newItem.antiRepeatCode && oldItem.name == newItem.name && oldItem.field == newItem.field
            } else true

        override fun areContentsTheSame(oldItem: BaseForm, newItem: BaseForm) = oldItem == newItem
    }).build())

    var name: CharSequence? = null

    var nameColor: (Context.() -> ColorStateList)? = null

    var field: String? = null

    @DrawableRes
    var icon: Int? = null

    var iconTint: (Context.() -> ColorStateList)? = null

    var dynamicGroup: Boolean = false

    var dynamicGroupDeleteConfirm: Boolean = false

    var dynamicMinPartCount: Int = 1

    var dynamicMaxPartCount: Int = Int.MAX_VALUE

    var dynamicGroupAddPartBlock: (FormCreatePart.() -> Unit)? = null

    var dynamicGroupNameFormatBlock: ((name: CharSequence?, index: Int) -> CharSequence)? = null

    val hasGroupTitle get() = name != null || dynamicGroup

    private var formEventListener: FormEventListener? = null

    init {
        // 必备组件
        addItemProvider(FormGroupTitleProvider(manager))
        addItemProvider(FormTextProvider(manager))

        addItemProvider(FormAddressProvider(manager))
        addItemProvider(FormButtonProvider(manager))
        addItemProvider(FormCheckboxProvider(manager))
        addItemProvider(FormDividerProvider(manager))
        addItemProvider(FormInputProvider(manager))
        addItemProvider(FormInputAutoCompleteProvider(manager))
        addItemProvider(FormLabelProvider(manager))
        addItemProvider(FormMenuProvider(manager))
        addItemProvider(FormRadioProvider(manager))
        addItemProvider(FormRatingProvider(manager))
        addItemProvider(FormSelectProvider(manager))
        addItemProvider(FormSliderProvider(manager))
        addItemProvider(FormSwitchProvider(manager))
        addItemProvider(FormTimeProvider(manager))
        addItemProvider(FormTipProvider(manager))
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
        nameColor = createGroup.groupNameColor
        field = createGroup.groupField
        icon = createGroup.groupIcon
        iconTint = createGroup.groupIconTint
        dynamicGroup = createGroup.dynamicGroup
        dynamicGroupDeleteConfirm = createGroup.dynamicGroupDeleteConfirm
        dynamicMinPartCount = createGroup.dynamicMinPartCount
        dynamicMaxPartCount = createGroup.dynamicMaxPartCount
        dynamicGroupAddPartBlock = createGroup.dynamicGroupAddPartBlock

        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        data = createGroup.createdFormPartList
        if (data.size < dynamicMinPartCount && dynamicGroupAddPartBlock != null) {
            val createPart = FormCreatePart()
            dynamicGroupAddPartBlock!!.invoke(createPart)
            data.add(createPart.createdFormGroupList)
        }
        data.forEach { it.forEach { item -> item.configData() } }
        update()
    }

    fun update(isHasPayloads: Boolean = false, block: (() -> Unit)? = null) {
        val groupIndex = manager.adapter.adapters.indexOf(this)
        val groupIsFirst = groupIndex <= 0
        val groupIsLast = groupIndex >= manager.adapter.adapters.lastIndex
        val list = ArrayList<BaseForm>()
        data.forEachIndexed { partIndex, part ->
            var index = 0
            val partName = if (dynamicGroup) {
                if (dynamicGroupNameFormatBlock != null) {
                    dynamicGroupNameFormatBlock!!.invoke(name, partIndex)
                } else {
                    "${name ?: "表"}${partIndex + 1}"
                }
            } else name ?: ""
            if (hasGroupTitle) {
                list.add(FormGroupTitle(partName, field).apply {
                    mode = if (dynamicGroup) {
                        if (partIndex == 0) {
                            if (data.size < dynamicMaxPartCount) {
                                FormGroupTitleMode.ADD
                            } else {
                                FormGroupTitleMode.NORMAL
                            }
                        } else {
                            FormGroupTitleMode.DELETE
                        }
                    } else {
                        FormGroupTitleMode.NORMAL
                    }
                    icon = this@FormGroupAdapter.icon
                    iconTint = this@FormGroupAdapter.iconTint
                    nameColor = this@FormGroupAdapter.nameColor
                    partPosition = partIndex
                    adapterPosition = 0
                    topBoundary = if (partIndex == 0 && groupIsFirst) {
                        FormBoundaryType.GLOBAL
                    } else FormBoundaryType.LOCAL
                })
                index++
            }
            part@ for (j in 0 until part.size) {
                val item = part[j]
                if (!item.isRealVisible(manager)) continue@part
                if (!item.isOnEdgeVisible) {
                    if (hasGroupTitle && index <= 1) continue@part
                    if (index == 0) continue@part
                    if (index == part.lastIndex) continue@part
                }
                item.partName = partName
                item.partPosition = partIndex
                item.adapterPosition = index
                item.topBoundary = if (index == 0) {
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
            if (!item.isOnEdgeVisible && index == list.lastIndex) {
                list.removeAt(index)
                continue@list
            }
            if (index == list.lastIndex) {
                item.bottomBoundary =
                    if (groupIsLast) FormBoundaryType.GLOBAL else FormBoundaryType.LOCAL
                continue@list
            }
            val nextItem = list[index + 1]
            item.bottomBoundary = if (nextItem.adapterPosition == 0) FormBoundaryType.LOCAL
            else FormBoundaryType.NONE
        }
        asyncDiffer.submitList(list) {
            notifyItemRangeChanged(0, list.size, if (isHasPayloads) "update" else null)
            block?.invoke()
        }
    }

    fun setFormEventListener(listener: FormEventListener?) {
        formEventListener = listener
    }

    fun onFormMenuClick(manager: BaseFormManager, item: BaseForm, view: View, position: Int) {
        if (item is FormGroupTitle) {
            if (item.mode == FormGroupTitleMode.ADD) {
                if (dynamicGroupAddPartBlock != null) {
                    val createPart = FormCreatePart()
                    dynamicGroupAddPartBlock!!.invoke(createPart)
                    data.add(createPart.createdFormGroupList.onEach { it.configData() })
                    update(true)
                    return
                }
            } else if (item.mode == FormGroupTitleMode.DELETE) {
                if (dynamicGroupDeleteConfirm) {
                    popupMenu {
                        dropdownGravity = Gravity.END
                        section {
                            title = "你确定删除${item.name}吗？"
                            isForceShowIcon = true
                            item {
                                label = "删除"
                                icon = R.drawable.form_ic_delete
                                val color = view.attrColor(androidx.appcompat.R.attr.colorError)
                                iconTint = ColorStateList.valueOf(color)
                                labelColor = color
                                onSelectedCallback {
                                    data.removeAt(item.partPosition)
                                    update(true)
                                }
                            }
                        }
                    }.show(view.context, view)
                } else {
                    data.removeAt(item.partPosition)
                    update(true)
                }
                return
            }
        }
        formEventListener?.onFormMenuClick(manager, item, view, position)
    }

    fun onFormClick(manager: BaseFormManager, item: BaseForm, view: View, position: Int) {
        formEventListener?.onFormClick(manager, item, view, position)
    }

    fun onFormContentChanged(manager: BaseFormManager, item: BaseForm, position: Int) {
        formEventListener?.onFormContentChanged(manager, item, position)
    }

    val currentList get() = asyncDiffer.currentList

    fun getItem(position: Int) = currentList[position]

    override fun getItemCount() = currentList.size

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
    ) = getItemProvider(holder.itemViewType)!!.onBindViewHolder(holder, position)

    @Suppress("DEPRECATION")
    override fun onBindViewHolder(
        holder: FormViewHolder, position: Int, payloads: MutableList<Any>
    ) = getItemProvider(holder.itemViewType)!!.onBindViewHolder(holder, position, payloads)

    override fun getItemViewType(position: Int) =
        if (manager.isEditable) getItem(position).type + typeIncrement else getItem(position).seeType + typeIncrement

    private fun getItemProvider(viewType: Int): BaseFormProvider<out BaseForm>? =
        itemProviders.get(viewType - typeIncrement)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        _recyclerView = WeakReference(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this._recyclerView = null
        adapterScope.cancel()
        adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }

    fun clearFocus() {
        recyclerView?.focusedChild?.clearFocus()
        recyclerView?.context?.hideIME()
    }

    @Throws(FormDataVerificationException::class)
    fun checkDataCorrectness() {
        data.forEach { part -> part.forEach { it.checkDataCorrectness(manager) } }
    }

    fun executeOutput(json: JSONObject) {
        data.forEach { part -> part.forEach { it.executeOutput(manager, json) } }
    }
}