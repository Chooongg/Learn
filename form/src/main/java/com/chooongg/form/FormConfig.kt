package com.chooongg.form

import android.content.Context
import com.chooongg.form.bean.FormFile
import com.chooongg.form.bean.FormSelect
import com.chooongg.form.bean.FormTime
import com.chooongg.form.bean.FormTimeRange

abstract class FormConfig {

    open fun openFilePicker(context: Context, item: FormFile) {

    }

    open fun openSelect(context: Context, item: FormSelect) {

    }

    open fun openTime(context: Context, item: FormTime) {

    }

    open fun openTimeRange(context: Context, item: FormTimeRange) {

    }

}
