package com.chooongg.form.bean

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.FloatRange
import com.chooongg.form.FormManager
import java.text.NumberFormat

class FormRating(name: CharSequence, field: String?) :
    BaseForm(FormManager.TYPE_RATING, name, field) {

    override var content: CharSequence? = null
        set(value) {
            field = value?.toString()?.toFloatOrNull()?.toString()
        }

    /**
     * 星星数量
     */
    @androidx.annotation.IntRange(from = 1)
    var numStars: Int = 5

    /**
     * 选择步长
     */
    @FloatRange(from = 0.0)
    var stepSize: Float = 1f

    var tint: (Context.() -> ColorStateList)? = null

    override fun transformContent(context: Context): CharSequence? {
        return NumberFormat.getPercentInstance()
            .format((content?.toString()?.toFloatOrNull() ?: 0f) / numStars)
    }

    override fun configData() {
        if (content == null) {
            content = 0f.toString()
        }
    }
}