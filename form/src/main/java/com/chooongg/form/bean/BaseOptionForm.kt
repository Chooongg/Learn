package com.chooongg.form.bean

import com.chooongg.form.enum.FormOptionsLoadScene
import com.chooongg.form.enum.FormOptionsLoadState
import com.chooongg.form.enum.FormOptionsLoaderMode
import com.chooongg.form.loader.OptionsLoadResult
import com.chooongg.form.loader.OptionsLoader

abstract class BaseOptionForm(type: Int, name: CharSequence, field: String?) :
    BaseForm(type, name, field) {

    /**
     * 选项列表
     */
    var options: List<Option>? = null

    /**
     * 选项加载模式
     */
    var optionsLoaderMode: FormOptionsLoaderMode = FormOptionsLoaderMode.IF_EMPTY

    /**
     * 选项加载事件
     */
    private var optionsLoader: OptionsLoader<out Option>? = null

    /**
     * 选项加载事件
     */
    fun optionsLoader(loader: OptionsLoader<out Option>?) {
        optionsLoader = loader
    }

    fun optionsLoader(block: suspend () -> OptionsLoadResult<Option>) {
        optionsLoader = object : OptionsLoader<Option>() {
            override suspend fun load() = block()
        }
    }

    fun getOptionsLoader() = optionsLoader

    /**
     * 是否需要加载选项
     */
    fun isNeedLoadOptions(scene: FormOptionsLoadScene): Boolean {
        if (optionsLoader == null) return false
        if (optionsLoader!!.state == FormOptionsLoadState.LOADING) return false
        return when (optionsLoaderMode) {
            FormOptionsLoaderMode.ALWAYS -> true
            FormOptionsLoaderMode.IF_EMPTY -> options.isNullOrEmpty()
            FormOptionsLoaderMode.BIND_ALWAYS -> scene == FormOptionsLoadScene.BIND
            FormOptionsLoaderMode.BIND_IF_EMPTY -> scene == FormOptionsLoadScene.BIND && options.isNullOrEmpty()
            FormOptionsLoaderMode.TRIGGER_ALWAYS -> scene == FormOptionsLoadScene.TRIGGER
            FormOptionsLoaderMode.TRIGGER_IF_EMPTY -> scene == FormOptionsLoadScene.TRIGGER && options.isNullOrEmpty()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseOptionForm) return false
        if (!super.equals(other)) return false

        if (options != other.options) return false
        if (optionsLoaderMode != other.optionsLoaderMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (options?.hashCode() ?: 0)
        result = 31 * result + optionsLoaderMode.hashCode()
        return result
    }
}