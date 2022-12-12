package com.chooongg.form.enum

/**
 * 选项加载模式
 */
enum class FormOptionsLoaderMode {
    /**
     * 总是加载
     */
    ALWAYS,

    /**
     * 为空加载
     */
    IF_EMPTY,

    /**
     * 绑定时总是加载
     */
    BIND_ALWAYS,

    /**
     * 绑定时为空加载
     */
    BIND_IF_EMPTY,

    /**
     * 触发时总是加载
     */
    TRIGGER_ALWAYS,

    /**
     * 触发时为空加载
     */
    TRIGGER_IF_EMPTY
}