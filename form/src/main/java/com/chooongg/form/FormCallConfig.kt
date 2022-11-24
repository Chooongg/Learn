package com.chooongg.form

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.getActivity
import com.chooongg.basic.ext.hideIME
import com.chooongg.core.popupMenu.popupMenu
import com.chooongg.form.bean.FormFile
import com.chooongg.form.bean.FormSelect
import com.chooongg.form.bean.FormTime
import com.chooongg.form.bean.FormTimeRange
import com.chooongg.form.enum.FormTimeMode
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

abstract class FormCallConfig {

    open fun openFilePicker(
        part: FormGroupAdapter,
        holder: FormViewHolder,
        parent: View,
        contentView: View,
        item: FormFile
    ) {

    }

    open fun openSelect(
        part: FormGroupAdapter,
        holder: FormViewHolder,
        parent: View,
        contentView: View,
        item: FormSelect
    ) {
        if (item.options == null) {
            item.getOptionsLoaderBlock()?.invoke {
                item.options = it
                openSelect(part, holder, parent, contentView, item)
            }
            return
        }
        popupMenu {
            section {
                item.options!!.forEach {
                    item {
                        label = it.getValue()
                        if (item.content == it.getKey()) {
                            labelColor = parent.attrColor(androidx.appcompat.R.attr.colorPrimary)
                        }
                        onSelectedCallback {
                            item.content = it.getKey()
                            part.notifyItemChanged(holder.bindingAdapterPosition, "update")
                        }
                    }
                }
            }
        }.show(parent.context, contentView)
    }

    open fun openTime(
        part: FormGroupAdapter,
        holder: FormViewHolder,
        parent: View,
        contentView: View,
        item: FormTime
    ) {
        val activity = parent.context.getActivity() as? AppCompatActivity ?: return
        activity.hideIME()
        activity.currentFocus?.clearFocus()
        when (item.mode) {
            FormTimeMode.TIME -> {
                val calendar = Calendar.getInstance()
                if (item.timeStamp != null) {
                    calendar.timeInMillis = item.timeStamp!!
                }
                MaterialTimePicker.Builder().setTitleText(item.name)
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                    .setMinute(calendar.get(Calendar.MINUTE))
                    .build().apply {
                        addOnPositiveButtonClickListener {
                            calendar.set(Calendar.HOUR_OF_DAY, hour)
                            calendar.set(Calendar.MINUTE, minute)
                            item.timeStamp = calendar.timeInMillis
                            part.notifyItemChanged(holder.bindingAdapterPosition, "update")
                        }
                    }.show(activity.supportFragmentManager, "timePicker")
            }
            FormTimeMode.DATE -> {

            }
            FormTimeMode.DATE_TIME -> {

            }
        }
    }

    open fun openTimeRange(
        part: FormGroupAdapter,
        holder: FormViewHolder,
        parent: View,
        contentView: View,
        item: FormTimeRange
    ) {


    }
}
