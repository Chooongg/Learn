package com.chooongg.form.bean

import com.chooongg.basic.ext.logE
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormChooseFileMode

class FormFile(name: CharSequence) : BaseForm(FormManager.TYPE_FILE, name) {

    /**
     * 选择模式
     */
    var chooseMode: FormChooseFileMode = FormChooseFileMode.FILE

    /**
     * 填充数据时忽略最大计数
     */
    var fillingIgnoreMaxCount: Boolean = true

    /**
     * 最小计数
     */
    @androidx.annotation.IntRange(from = 1)
    var minCount: Int = 1

    /**
     * 最大计数
     */
    @androidx.annotation.IntRange(from = 1)
    var maxCount: Int = 1


    override fun seeOnlyType(type: Int) {
        logE("Form", "无效的设置")
    }

}