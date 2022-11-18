package com.chooongg.form.bean

import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.chooongg.form.FormDataVerificationException
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.enum.FormOutPutMode
import com.chooongg.form.enum.FormVisibilityMode
import org.json.JSONObject

abstract class BaseForm(
    /**
     * 类型
     */
    val type: Int,
    /**
     * 名称
     */
    var name: CharSequence,
    /**
     * 字段名
     */
    var field: String?
) {

    var adapterPosition: Int = -1
        internal set
    var adapterTopBoundary: FormBoundaryType = FormBoundaryType.NONE
        internal set
    var adapterBottomBoundary: FormBoundaryType = FormBoundaryType.NONE
        internal set

    /**
     * 只读状态类型
     */
    open var seeType: Int = type

    /**
     * 扩展字段和内容
     */
    private var extensionFieldAndContent: HashMap<String, CharSequence?>? = null

    /**
     * 提示文字
     */
    var hint: CharSequence? = null

    /**
     * 内容
     */
    var content: CharSequence? = null

    /**
     * 是否是必填项
     */
    var isMust: Boolean = false

    /**
     * 是否可见
     */
    var isVisible: Boolean = true

    /**
     * 可见模式
     */
    var visibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 是否启用
     */
    var isEnabled: Boolean = true

    open fun seeOnlyType(type: Int) {
        seeType = type
    }

    /**
     * 菜单图标
     */
    @DrawableRes
    open var menuIcon: Int? = null

    /**
     * 菜单图标着色
     */
    open var menuIconTint: ColorStateList? = null

    /**
     * 菜单可见模式
     */
    open var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 忽略名称长度限制
     */
    var ignoreNameEms: Boolean = false

    /**
     * 输出模式
     */
    var outPutMode: FormOutPutMode = FormOutPutMode.ONLY_VISIBLE

    /**
     * 初始化完成后配置数据
     */
    open fun configData() = Unit

    /**
     * 设置扩展内容
     */
    fun setExtensionContent(key: String, value: CharSequence?) {
        if (value == null) {
            if (extensionFieldAndContent != null) {
                extensionFieldAndContent!!.remove(key)
                if (extensionFieldAndContent!!.isEmpty()) {
                    extensionFieldAndContent = null
                }
            }
        } else {
            if (extensionFieldAndContent == null) {
                extensionFieldAndContent = HashMap()
            }
            extensionFieldAndContent!![key] = value
        }
    }

    /**
     * 获取扩展内容
     */
    fun getExtensionContent(key: String): CharSequence? = extensionFieldAndContent?.get(key)

    /**
     * 是否有扩展内容
     */
    fun hasExtensionContent(key: String): Boolean =
        extensionFieldAndContent?.containsKey(key) ?: false

    /**
     * 快照扩展字段和内容
     */
    fun snapshotExtensionFieldAndContent(): Map<String, CharSequence?> =
        extensionFieldAndContent?.toMap() ?: emptyMap()

    /**
     * 转换内容
     */
    open fun transformContent(): CharSequence? = content

    /**
     * 检查数据正确性
     */
    @Throws(FormDataVerificationException::class)
    open fun checkDataCorrectness(manager: FormManager) {
        if (isMust && content.isNullOrEmpty()) {
            if (outPutMode == FormOutPutMode.ALWAYS) {
                throw FormDataVerificationException(field, "你需要补充$name")
            } else if (outPutMode == FormOutPutMode.ONLY_VISIBLE) {
                if (isVisible) {
                    if (visibilityMode == FormVisibilityMode.ALWAYS) {
                        throw FormDataVerificationException(field, "你需要补充$name")
                    }
                }
            }
        }
    }

    /**
     * 输出数据
     */
    open fun outputData(manager: FormManager, json: JSONObject) {
        if (field != null && content != null) {
            json.putOpt(field, content)
        }
        extensionFieldAndContent?.forEach {
            if (it.value != null) {
                json.put(it.key, it.value)
            }
        }
    }

    /**
     * 获取真实的可见性
     */
    open fun isRealVisible(manager: FormManager): Boolean {
        if (!isVisible) return false
        return when (visibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_SEE -> !manager.isEditable
            FormVisibilityMode.ONLY_EDIT -> manager.isEditable
        }
    }

    /**
     * 获取真实的菜单可见性
     */
    open fun isRealMenuVisible(manager: FormManager): Boolean {
        return when (menuVisibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_SEE -> !manager.isEditable
            FormVisibilityMode.ONLY_EDIT -> manager.isEditable
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseForm) return false

        if (type != other.type) return false
        if (name != other.name) return false
        if (field != other.field) return false
        if (adapterPosition != other.adapterPosition) return false
        if (adapterTopBoundary != other.adapterTopBoundary) return false
        if (seeType != other.seeType) return false
        if (extensionFieldAndContent != other.extensionFieldAndContent) return false
        if (hint != other.hint) return false
        if (content != other.content) return false
        if (isMust != other.isMust) return false
        if (isVisible != other.isVisible) return false
        if (visibilityMode != other.visibilityMode) return false
        if (isEnabled != other.isEnabled) return false
        if (menuIcon != other.menuIcon) return false
        if (menuIconTint != other.menuIconTint) return false
        if (menuVisibilityMode != other.menuVisibilityMode) return false
        if (ignoreNameEms != other.ignoreNameEms) return false
        if (outPutMode != other.outPutMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type
        result = 31 * result + name.hashCode()
        result = 31 * result + (field?.hashCode() ?: 0)
        result = 31 * result + adapterPosition
        result = 31 * result + adapterTopBoundary.hashCode()
        result = 31 * result + adapterBottomBoundary.hashCode()
        result = 31 * result + seeType
        result = 31 * result + (extensionFieldAndContent?.hashCode() ?: 0)
        result = 31 * result + (hint?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + isMust.hashCode()
        result = 31 * result + isVisible.hashCode()
        result = 31 * result + visibilityMode.hashCode()
        result = 31 * result + isEnabled.hashCode()
        result = 31 * result + (menuIcon ?: 0)
        result = 31 * result + (menuIconTint?.hashCode() ?: 0)
        result = 31 * result + menuVisibilityMode.hashCode()
        result = 31 * result + ignoreNameEms.hashCode()
        result = 31 * result + outPutMode.hashCode()
        return result
    }
}