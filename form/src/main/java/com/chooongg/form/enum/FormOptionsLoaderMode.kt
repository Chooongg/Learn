package com.chooongg.form.enum

/**
 * 选项加载模式
 */
enum class FormOptionsLoaderMode {
    /**
     * 绑定时
     */
    BINDING,

    /**
     * 选择时
     */
    SELECT_ALWAYS,

    /**
     * 选择时
     * 选项为空时
     */
    SELECT_EMPTY,
}