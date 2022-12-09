package com.chooongg.form.bean

import com.chooongg.form.FormManager

class FormSlider(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_SLIDER, name, field) {

    var valueFrom: Float = 0f

    var valueTo: Float = 100f

    var stepSize: Float = 0f

    private var labelFormatter: ((Float) -> String)? = null

    fun labelFormatter(block: ((Float) -> String)?) {
        labelFormatter = block
    }

    fun getLabelFormatter() = labelFormatter
}