package com.chooongg.form.provider

import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatRatingBar
import com.chooongg.basic.ext.attrColor
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.FormRating

class FormRatingProvider(manager: BaseFormManager) : BaseFormProvider<FormRating>(manager) {
    override val itemViewType: Int get() = FormManager.TYPE_RATING
    override val layoutId: Int get() = R.layout.form_item_rating
    override fun onBindViewHolder(holder: FormViewHolder, item: FormRating) {
        with(holder.getView<AppCompatRatingBar>(R.id.form_rating)) {
            isEnabled = item.isRealEnable(manager)
            numStars = item.numStars
            stepSize = item.stepSize
            onRatingBarChangeListener = null
            rating = item.content?.toString()?.toFloatOrNull() ?: 0f
            progressTintList = item.tint?.invoke(context) ?: ColorStateList.valueOf(
                attrColor(androidx.appcompat.R.attr.colorPrimary)
            )
            setOnRatingBarChangeListener { _, rating, _ ->
                item.content = rating.toString()
                groupAdapter?.onFormContentChanged(manager, item, holder.absoluteAdapterPosition)
            }
        }
    }
}