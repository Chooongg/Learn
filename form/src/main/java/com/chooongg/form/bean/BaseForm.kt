package com.chooongg.form.bean

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.DrawableRes
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormDataVerificationException
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.enum.FormEnableMode
import com.chooongg.form.enum.FormOutputMode
import com.chooongg.form.enum.FormVisibilityMode
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButton.IconGravity
import org.json.JSONObject
import kotlin.random.Random

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

    /**
     * 扩展字段和内容
     */
    private var extensionFieldAndContent: HashMap<String, CharSequence?>? = null

    // 片段坐标
    var partPosition: Int = -1
        internal set

    // 片段名称
    var partName: CharSequence? = null
        internal set

    // 适配器坐标
    var adapterPosition: Int = -1
        internal set

    // 顶部边缘类型
    var topBoundary: FormBoundaryType = FormBoundaryType.NONE
        internal set

    // 底部边缘类型
    var bottomBoundary: FormBoundaryType = FormBoundaryType.NONE
        internal set

    /**
     * 只读状态类型
     */
    open var seeType: Int = type
        protected set

    /**
     * 是否是必填项
     */
    open var isMust: Boolean = false

    /**
     * 名称文本颜色
     */
    open var nameTextColor: (Context.() -> ColorStateList)? = null

    /**
     * 提示文字
     */
    open var hint: CharSequence? = null

    /**
     * 内容
     */
    open var content: CharSequence? = null

    /**
     * 是否可见
     */
    open var isVisible: Boolean = true

    /**
     * 可见模式
     */
    open var visibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 是否启用
     */
    open var enableMode: FormEnableMode = FormEnableMode.ONLY_EDIT

    /**
     * 菜单文本
     */
    open var menuText: CharSequence? = null

    /**
     * 菜单文本颜色
     */
    open var menuTextColor: (Context.() -> ColorStateList)? = null

    /**
     * 菜单图标重力
     */
    @IconGravity
    open var menuIconGravity: Int = MaterialButton.ICON_GRAVITY_START

    /**
     * 菜单图标
     */
    @DrawableRes
    open var menuIcon: Int? = null

    /**
     * 菜单图标
     */
    open var menuIconTint: (Context.() -> ColorStateList)? = null

    /**
     * 菜单可见模式
     */
    open var menuVisibilityMode: FormVisibilityMode = FormVisibilityMode.ALWAYS

    /**
     * 菜单可用模式
     */
    open var menuEnableMode: FormEnableMode = FormEnableMode.ALWAYS

    /**
     * 忽略名称长度限制
     */
    open var ignoreNameEms: Boolean = false

    /**
     * 是否在 Part 边缘可见
     */
    open var isOnEdgeVisible = true

    /**
     * 输出模式
     */
    open var outputMode: FormOutputMode = FormOutputMode.ALWAYS

    /**
     * 扩展参数输出模式
     */
    open var extensionOutputMode: FormOutputMode = FormOutputMode.ALWAYS

    private var customOutputBlock: ((json: JSONObject) -> Unit)? = null

    val antiRepeatCode = System.currentTimeMillis() + Random.nextLong(3000)

    /**
     * 初始化完成后配置数据
     */
    open fun configData() = Unit

    /**
     * 设置只读类型
     */
    open fun seeOnlyType(type: Int) {
        if (type == this.type || type == FormManager.TYPE_TEXT) {
            seeType = type
        } else throw ClassCastException("seeOnlyType() only support this.type or FormManager.TYPE_TEXT")
    }

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
    open fun transformContent(context: Context): CharSequence? = content

    /**
     * 是否验证或输出
     */
    fun whetherToCheckDataOrOutput(manager: BaseFormManager): Boolean {
        if (outputMode == FormOutputMode.ONLY_VISIBLE && !isRealVisible(manager)) return false
        return true
    }

    /**
     * 检查数据正确性
     */
    @Throws(FormDataVerificationException::class)
    open fun checkDataCorrectness(manager: BaseFormManager) {
        if (!whetherToCheckDataOrOutput(manager)) return
        if (isMust && content.isNullOrEmpty()) {
            throw FormDataVerificationException(partName, field, name)
        }
    }

    /**
     * 执行输入数据
     */
    fun executeOutput(manager: BaseFormManager, json: JSONObject) {
        if (!whetherToCheckDataOrOutput(manager)) return
        if (customOutputBlock != null) {
            customOutputBlock!!.invoke(json)
            return
        }
        extensionFieldAndContent?.forEach {
            if (it.value != null) {
                json.put(it.key, it.value)
            }
        }
        outputData(manager, json)
    }

    fun customOutput(block: ((json: JSONObject) -> Unit)?) {
        customOutputBlock = block
    }

    fun snapshotCustomOutputBlock() = customOutputBlock

    /**
     * 输出数据
     */
    open fun outputData(manager: BaseFormManager, json: JSONObject) {
        if (field != null && content != null) {
            json.putOpt(field, content)
        }
    }

    /**
     * 获取真实的可见性
     */
    open fun isRealVisible(manager: BaseFormManager): Boolean {
        if (!isVisible) return false
        return when (visibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_SEE -> !manager.isEditable
            FormVisibilityMode.ONLY_EDIT -> manager.isEditable
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 获取真实的可用性
     */
    open fun isRealEnable(manager: BaseFormManager): Boolean {
        return when (enableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ONLY_SEE -> !manager.isEditable
            FormEnableMode.ONLY_EDIT -> manager.isEditable
            FormEnableMode.NEVER -> false
        }
    }

    /**
     * 获取真实的菜单可见性
     */
    open fun isRealMenuVisible(manager: BaseFormManager): Boolean {
        val isHaveMenu = menuText != null || menuIcon != null
        return when (menuVisibilityMode) {
            FormVisibilityMode.ALWAYS -> isHaveMenu
            FormVisibilityMode.ONLY_SEE -> isHaveMenu && !manager.isEditable
            FormVisibilityMode.ONLY_EDIT -> isHaveMenu && manager.isEditable
            FormVisibilityMode.NEVER -> false
        }
    }

    /**
     * 获取真实的可用性
     */
    open fun isRealMenuEnable(manager: BaseFormManager): Boolean {
        return when (menuEnableMode) {
            FormEnableMode.ALWAYS -> true
            FormEnableMode.ONLY_SEE -> !manager.isEditable
            FormEnableMode.ONLY_EDIT -> manager.isEditable
            FormEnableMode.NEVER -> false
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseForm) return false

        if (type != other.type) return false
        if (name != other.name) return false
        if (field != other.field) return false
        if (extensionFieldAndContent != other.extensionFieldAndContent) return false
        if (partPosition != other.partPosition) return false
        if (partName != other.partName) return false
        if (adapterPosition != other.adapterPosition) return false
        if (topBoundary != other.topBoundary) return false
        if (bottomBoundary != other.bottomBoundary) return false
        if (seeType != other.seeType) return false
        if (isMust != other.isMust) return false
        if (hint != other.hint) return false
        if (content != other.content) return false
        if (isVisible != other.isVisible) return false
        if (visibilityMode != other.visibilityMode) return false
        if (enableMode != other.enableMode) return false
        if (menuText != other.menuText) return false
        if (menuIconGravity != other.menuIconGravity) return false
        if (menuIcon != other.menuIcon) return false
        if (menuVisibilityMode != other.menuVisibilityMode) return false
        if (menuEnableMode != other.menuEnableMode) return false
        if (ignoreNameEms != other.ignoreNameEms) return false
        if (isOnEdgeVisible != other.isOnEdgeVisible) return false
        if (outputMode != other.outputMode) return false
        if (extensionOutputMode != other.extensionOutputMode) return false
        if (antiRepeatCode != other.antiRepeatCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type
        result = 31 * result + name.hashCode()
        result = 31 * result + (field?.hashCode() ?: 0)
        result = 31 * result + (extensionFieldAndContent?.hashCode() ?: 0)
        result = 31 * result + partPosition
        result = 31 * result + (partName?.hashCode() ?: 0)
        result = 31 * result + adapterPosition
        result = 31 * result + topBoundary.hashCode()
        result = 31 * result + bottomBoundary.hashCode()
        result = 31 * result + seeType
        result = 31 * result + isMust.hashCode()
        result = 31 * result + (hint?.hashCode() ?: 0)
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + isVisible.hashCode()
        result = 31 * result + visibilityMode.hashCode()
        result = 31 * result + enableMode.hashCode()
        result = 31 * result + (menuText?.hashCode() ?: 0)
        result = 31 * result + menuIconGravity.hashCode()
        result = 31 * result + (menuIcon ?: 0)
        result = 31 * result + menuVisibilityMode.hashCode()
        result = 31 * result + menuEnableMode.hashCode()
        result = 31 * result + ignoreNameEms.hashCode()
        result = 31 * result + isOnEdgeVisible.hashCode()
        result = 31 * result + outputMode.hashCode()
        result = 31 * result + extensionOutputMode.hashCode()
        result = 31 * result + antiRepeatCode.hashCode()
        return result
    }
}