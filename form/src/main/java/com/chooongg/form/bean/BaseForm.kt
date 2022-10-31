package com.chooongg.form.bean

import com.chooongg.form.FormManager
import com.chooongg.form.VisibleMode
import org.json.JSONObject

abstract class BaseForm(val type: Int, var name: CharSequence?) {

    /**
     * 只读状态类型
     */
    open val seeType: Int get() = type

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
    var visibleMode: VisibleMode = VisibleMode.ALWAYS

    /**
     * 忽略名称长度限制
     */
    var keepNameEms: Boolean = false

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

    open fun outputData(manager: FormManager, json: JSONObject) {

    }
}