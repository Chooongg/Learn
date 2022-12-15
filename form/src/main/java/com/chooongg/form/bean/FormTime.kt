package com.chooongg.form.bean

import android.content.Context
import com.chooongg.basic.utils.TimeUtils
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormTimeMode
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DayViewDecorator
import com.google.android.material.timepicker.TimeFormat
import org.json.JSONObject

class FormTime(name: CharSequence, field: String?, val mode: FormTimeMode) :
    BaseForm(FormManager.TYPE_TIME, name, field) {

    companion object {
        const val FORMAT_TIMESTAMP = "stamp"
    }

    override var seeType: Int = FormManager.TYPE_TEXT

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

    var showFormat: String? = when (mode) {
        FormTimeMode.TIME -> "HH:mm"
        FormTimeMode.DATE -> "yyyy年MM月dd日"
        FormTimeMode.DATE_TIME -> "yyyy年MM月dd日 HH:mm"
    }

    var importFormat: String? = null

    var outputFormat: String? = null

    var calendarConstraints: CalendarConstraints? = null

    var dayViewDecorator: DayViewDecorator? = null

    @TimeFormat
    var timeFormatMode: Int = TimeFormat.CLOCK_12H

    override fun configData() {
        timeStamp = if (content != null) {
            if (importFormat == FORMAT_TIMESTAMP) {
                content?.toString()?.toLongOrNull()
            } else {
                TimeUtils.string2Millis(content!!.toString(), importFormat ?: format)
            }
        } else null
    }

    override fun transformContent(context: Context): CharSequence? {
        return if (timeStamp != null) {
            if (showFormat != null) {
                TimeUtils.millis2String(timeStamp!!, showFormat!!)
            } else timeStamp!!.toString()
        } else null
    }

    override fun outputData(manager: BaseFormManager, json: JSONObject) {
        if (field != null && timeStamp != null) {
            json.putOpt(
                field, if (outputFormat == FORMAT_TIMESTAMP) {
                    content?.toString()?.toLongOrNull()
                } else {
                    TimeUtils.millis2String(timeStamp!!, outputFormat ?: format)
                }
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormTime) return false
        if (!super.equals(other)) return false

        if (mode != other.mode) return false
        if (timeStamp != other.timeStamp) return false
        if (format != other.format) return false
        if (showFormat != other.showFormat) return false
        if (importFormat != other.importFormat) return false
        if (outputFormat != other.outputFormat) return false
        if (calendarConstraints != other.calendarConstraints) return false
        if (dayViewDecorator != other.dayViewDecorator) return false
        if (timeFormatMode != other.timeFormatMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + mode.hashCode()
        result = 31 * result + (timeStamp?.hashCode() ?: 0)
        result = 31 * result + format.hashCode()
        result = 31 * result + (showFormat?.hashCode() ?: 0)
        result = 31 * result + (importFormat?.hashCode() ?: 0)
        result = 31 * result + (outputFormat?.hashCode() ?: 0)
        result = 31 * result + (calendarConstraints?.hashCode() ?: 0)
        result = 31 * result + (dayViewDecorator?.hashCode() ?: 0)
        result = 31 * result + timeFormatMode
        return result
    }


}