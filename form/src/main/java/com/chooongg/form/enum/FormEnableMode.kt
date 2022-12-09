package com.chooongg.form.enum

/**
 * 可用模式
 */
enum class FormEnableMode {
    /**
     * 总是可用
     */
    ALWAYS,

    /**
     * 仅只读时可用
     */
    ONLY_SEE,

    /**
     * 仅编辑时可用
     */
    ONLY_EDIT,

    /**
     * 总不可用
     */
    NEVER
}