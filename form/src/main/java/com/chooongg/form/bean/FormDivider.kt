package com.chooongg.form.bean

import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.chooongg.form.FormManager

class FormDivider(name: CharSequence) : BaseForm(FormManager.TYPE_DIVIDER, name) {

    /**
     * 分割线颜色
     */
    @ColorInt
    var color: Int? = null

    /**
     * 分割线厚度
     */
    @Px
    var thickness:Int? = null

    /**
     * 分割线 End 距离
     */
    @Px
    var insetEnd:Int? = null

    /**
     * 分割线 Start 距离
     */
    @Px
    var insetStart:Int? = null
}