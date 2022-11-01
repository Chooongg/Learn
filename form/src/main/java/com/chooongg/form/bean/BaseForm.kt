package com.chooongg.form.bean

import android.view.View
import com.chooongg.form.FormDataVerificationException
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormOutPutMode
import com.chooongg.form.enum.FormVisibilityMode
import org.json.JSONObject

abstract class BaseForm(val type: Int, var name: CharSequence) {

    /**
     * 只读状态类型
     */
    open val seeOnlyType: Int get() = type

    /**
     * 扩展字段和内容
     */
    private var extensionFieldAndContent: HashMap<String, CharSequence?>? = null

    /**
     * 提示文字
     */
    var hint: CharSequence? = null

    /**
     * 字段名
     */
    var field: String? = null

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
     * 忽略名称长度限制
     */
    var keepNameEms: Boolean = false

    /**
     * 输出模式
     */
    var outPutMode: FormOutPutMode = FormOutPutMode.ONLY_VISIBLE

    /**
     * ItemView点击事件
     */
    internal var itemClickBlock: ((View) -> Unit)? = null

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
     * Item 点击事件
     */
    fun doOnClick(block: ((View) -> Unit)?) {
        itemClickBlock = block
    }

    @Throws(FormDataVerificationException::class)
    open fun checkDataIsCorrect(manager: FormManager) {
        if (isMust && content.isNullOrEmpty()) {
            if (outPutMode == FormOutPutMode.ALWAYS) {
                throw FormDataVerificationException(field, "${name}不可为空")
            } else if (outPutMode == FormOutPutMode.ONLY_VISIBLE) {
                if (isVisible) {
                    if (visibilityMode == FormVisibilityMode.ALWAYS) {
                        throw FormDataVerificationException(field, "${name}不可为空")
                    }
                }
            }
        }
    }

    open fun outputData(manager: FormManager, json: JSONObject) {

    }

    /**
     * 获取真实的可见性
     */
    fun isRealVisible(manager: FormManager): Boolean {
        if (!isVisible) return false
        return when (visibilityMode) {
            FormVisibilityMode.ALWAYS -> true
            FormVisibilityMode.ONLY_SEE -> !manager.isEditable
            FormVisibilityMode.ONLY_EDIT -> manager.isEditable
        }
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}