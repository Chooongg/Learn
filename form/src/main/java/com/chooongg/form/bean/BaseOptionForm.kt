package com.chooongg.form.bean

abstract class BaseOptionForm(type: Int, name: CharSequence) : BaseForm(type, name) {

    /**
     * 选项列表
     */
    private var options: List<Option>? = null

    /**
     * 选项加载事件
     */
    private var optionsLoaderBlock: (((List<Option>?) -> Unit) -> Unit)? = null

    /**
     * 选项加载事件
     */
    fun optionsLoader(block: ((List<Option>?) -> Unit) -> Unit) {
        optionsLoaderBlock = block
    }
}