package com.chooongg.form.provider

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePaddingRelative
import com.chooongg.basic.ext.doOnClick
import com.chooongg.basic.ext.getActivity
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.basic.ext.showToast
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormTime
import com.chooongg.form.enum.FormTimeMode
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*

class FormTimeProvider(manager: BaseFormManager) : BaseFormProvider<FormTime>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_TIME
    override val layoutId: Int get() = R.layout.form_item_time
    override fun onBindViewHolder(holder: FormViewHolder, item: FormTime) {
        with(holder.getView<MaterialButton>(R.id.form_btn_content)) {
            val verticalPadding = resDimensionPixelSize(R.dimen.formItemVertical)
            updatePaddingRelative(
                top = verticalPadding,
                bottom = verticalPadding
            )
            isEnabled = item.isRealEnable(manager)
            text = item.transformContent(context)
            hint = item.hint ?: context.getString(R.string.form_select_hint)
            doOnClick { showPicker(holder, this, item) }
        }
    }

    private fun showPicker(holder: FormViewHolder, anchor: MaterialButton, item: FormTime) {
        val activity = context.getActivity() as? AppCompatActivity
        if (activity == null) {
            showToast(R.string.form_time_start_error)
            return
        }
        val calendar = Calendar.getInstance()
        if (item.timeStamp != null) {
            calendar.timeInMillis = item.timeStamp!!
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
        }
        calendar.set(Calendar.SECOND, 0)
        when (item.mode) {
            FormTimeMode.TIME -> {
                MaterialTimePicker.Builder().setTitleText(item.name)
                    .setTimeFormat(item.timeFormatMode)
                    .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                    .setMinute(calendar.get(Calendar.MINUTE))
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            calendar.set(Calendar.HOUR_OF_DAY, hour)
                            calendar.set(Calendar.MINUTE, minute)
                            item.timeStamp = calendar.timeInMillis
                            anchor.text = item.transformContent(anchor.context)
                            groupAdapter?.onFormContentChanged(
                                manager, item, holder.absoluteAdapterPosition
                            )
                        }
                    }.show(activity.supportFragmentManager, "FormTimePicker")
            }
            FormTimeMode.DATE -> {
                MaterialDatePicker.Builder.datePicker().setTitleText(item.name)
                    .setSelection(calendar.timeInMillis + calendar.timeZone.rawOffset)
                    .setCalendarConstraints(item.calendarConstraints)
                    .setDayViewDecorator(item.dayViewDecorator)
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            calendar.timeInMillis = it - calendar.timeZone.rawOffset
                            item.timeStamp = calendar.timeInMillis
                            anchor.text = item.transformContent(anchor.context)
                            groupAdapter?.onFormContentChanged(
                                manager, item, holder.absoluteAdapterPosition
                            )
                        }
                    }.show(activity.supportFragmentManager, "FormDatePicker")
            }
            FormTimeMode.DATE_TIME -> {
                val newCalendar = Calendar.getInstance()
                newCalendar.timeInMillis = calendar.timeInMillis
                MaterialDatePicker.Builder.datePicker().setTitleText(item.name)
                    .setSelection(newCalendar.timeInMillis + newCalendar.timeZone.rawOffset)
                    .setCalendarConstraints(item.calendarConstraints)
                    .setDayViewDecorator(item.dayViewDecorator)
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            newCalendar.timeInMillis = it - newCalendar.timeZone.rawOffset
                            newCalendar.set(
                                Calendar.HOUR_OF_DAY,
                                calendar.get(Calendar.HOUR_OF_DAY)
                            )
                            newCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE))
                            MaterialTimePicker.Builder().setTitleText(item.name)
                                .setTimeFormat(item.timeFormatMode)
                                .setHour(newCalendar.get(Calendar.HOUR_OF_DAY))
                                .setMinute(newCalendar.get(Calendar.MINUTE))
                                .build().apply {
                                    addOnPositiveButtonClickListener {
                                        newCalendar.set(Calendar.HOUR_OF_DAY, hour)
                                        newCalendar.set(Calendar.MINUTE, minute)
                                        item.timeStamp = newCalendar.timeInMillis
                                        anchor.text = item.transformContent(anchor.context)
                                        groupAdapter?.onFormContentChanged(
                                            manager, item, holder.absoluteAdapterPosition
                                        )
                                    }
                                }.show(activity.supportFragmentManager, "FormTimePicker")
                        }
                    }.show(activity.supportFragmentManager, "FormDatePicker")
            }
        }
    }
}