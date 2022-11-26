package com.chooongg.form.bean

import android.content.Context
import com.chooongg.basic.utils.TimeUtils
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormTimeMode

class FormTime(name: CharSequence, field: String?, val mode: FormTimeMode) :
    BaseForm(FormManager.TYPE_TIME, name, field) {

    var timeStamp: Long? = null
        set(value) {
            field = value
            content = if (value == null) null
            else TimeUtils.millis2String(timeStamp!!, outputFormat ?: format)
        }

    var format: String = when (mode) {
        FormTimeMode.TIME -> "HH:mm"
        FormTimeMode.DATE -> "yyyy-MM-dd"
        FormTimeMode.DATE_TIME -> "yyyy-MM-dd HH:mm"
    }

    var showFormat: String = when (mode) {
        FormTimeMode.TIME -> "HH:mm"
        FormTimeMode.DATE -> "yyyy年MM月dd日"
        FormTimeMode.DATE_TIME -> "yyyy年MM月dd日 HH:mm"
    }

    var importFormat: String? = null

    var outputFormat: String? = null

    override fun configData() {
        if (content != null) {
            timeStamp = TimeUtils.string2Millis(content!!.toString(), importFormat ?: format)
            if (outputFormat != null && (importFormat ?: format) != outputFormat) {
                content = TimeUtils.millis2String(timeStamp!!, outputFormat!!)
            }
        }
    }

    override fun transformContent(context: Context): CharSequence? {
        return if (timeStamp != null) {
            TimeUtils.millis2String(timeStamp!!, showFormat)
        } else null
    }
}