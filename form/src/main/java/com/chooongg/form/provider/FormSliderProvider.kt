package com.chooongg.form.provider

import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormSlider
import com.google.android.material.slider.Slider

class FormSliderProvider(manager: FormManager) : BaseFormProvider<FormSlider>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_SLIDER
    override val layoutId: Int get() = R.layout.form_item_slider
    override fun onBindViewHolder(holder: FormViewHolder, item: FormSlider) {
        with(holder.getView<Slider>(R.id.form_slider_content)) {
            if (tag is Slider.OnSliderTouchListener) {
                removeOnSliderTouchListener(tag as Slider.OnSliderTouchListener)
            }
            isEnabled = item.isRealEnable(manager)
            valueFrom = item.valueFrom
            valueTo = item.valueTo
            stepSize = item.stepSize
            var contentValue = valueFrom
            if (!item.content.isNullOrEmpty()) {
                val temp = item.content!!.toString().toFloatOrNull()
                if (temp != null) {
                    contentValue = temp
                } else item.content = valueFrom.toString()
            } else item.content = valueFrom.toString()
            value = contentValue
            setLabelFormatter(item.getLabelFormatter())
            val listener = object : Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) = Unit
                override fun onStopTrackingTouch(slider: Slider) {
                    item.content = value.toString()
                    groupAdapter?.onFormContentChanged(
                        manager, item, holder.absoluteAdapterPosition
                    )
                }
            }
            addOnSliderTouchListener(listener)
            tag = listener
        }
    }
}