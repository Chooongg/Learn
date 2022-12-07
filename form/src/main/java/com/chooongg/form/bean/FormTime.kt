package com.chooongg.form.bean

import android.content.Context
import com.chooongg.basic.ext.resString
import com.chooongg.basic.utils.TimeUtils
import com.chooongg.form.FormManager
import com.chooongg.form.R
import com.chooongg.form.enum.FormTimeMode
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DayViewDecorator
import org.json.JSONObject

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
        FormTimeMode.DATE -> "yyyy年MM月dd日 HH:mm"
        FormTimeMode.DATE_TIME -> "yyyy年MM月dd日 HH:mm"
    }

    var importFormat: String? = null

    var outputFormat: String? = null

    var calendarConstraints: CalendarConstraints? = null

    var dayViewDecorator: DayViewDecorator? = null

    override fun configData() {
        if (content != null) {
            timeStamp = TimeUtils.string2Millis(content!!.toString(), importFormat ?: format)
        }
    }

    override fun transformContent(context: Context): CharSequence? {
        return if (timeStamp != null) {
                TimeUtils.millis2String(timeStamp!!, showFormat)
        } else null
    }

    override fun outputData(manager: FormManager, json: JSONObject) {
        if (field != null && timeStamp != null) {
            json.putOpt(field, timeStamp)
        }
        snapshotExtensionFieldAndContent().forEach {
            if (it.value != null) {
                json.put(it.key, it.value)
            }
        }
    }
}