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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FormSlider) return false
        if (!super.equals(other)) return false

        if (valueFrom != other.valueFrom) return false
        if (valueTo != other.valueTo) return false
        if (stepSize != other.stepSize) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + valueFrom.hashCode()
        result = 31 * result + valueTo.hashCode()
        result = 31 * result + stepSize.hashCode()
        return result
    }
}