package com.chooongg.form.bean

import android.content.Context
import androidx.annotation.Px
import com.chooongg.basic.ext.logE
import com.chooongg.form.FormManager

class FormDivider : BaseForm(FormManager.TYPE_DIVIDER, "", null) {

    /**
     * 分割线颜色
     */
    var color: (Context.() -> Int)? = null

    /**
     * 分割线厚度
     */
    @Px
    var thickness: Int? = null

    /**
     * 分割线 End 距离
     */
    @Px
    var insetEnd: Int? = null

    /**
     * 分割线 Start 距离
     */
    @Px
    var insetStart: Int? = null

    /**
     * 是否在 Part 边缘可见
     */
    override var isOnEdgeVisible = false

    override fun seeOnlyType(type: Int) {
        logE("Form", "无效的设置")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormDivider) return false
        if (!super.equals(other)) return false

        if (thickness != other.thickness) return false
        if (insetEnd != other.insetEnd) return false
        if (insetStart != other.insetStart) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (thickness ?: 0)
        result = 31 * result + (insetEnd ?: 0)
        result = 31 * result + (insetStart ?: 0)
        return result
    }
}