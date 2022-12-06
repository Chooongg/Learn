package com.chooongg.form.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import com.chooongg.form.R
import com.google.android.material.color.MaterialColors
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class FormAutoCompleteTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialAutoCompleteTextView(context, attributeSet, defStyleAttr) {

    override fun setSimpleItems(stringArray: Array<out String>) {
        setAdapter(MaterialSearchArrayAdapter<Any>(context, R.layout.form_auto_complete_item, stringArray))
    }

    override fun setSimpleItemSelectedColor(simpleItemSelectedColor: Int) {
        super.setSimpleItemSelectedColor(simpleItemSelectedColor)
        if (adapter is MaterialSearchArrayAdapter<*>) {
            (adapter as MaterialSearchArrayAdapter<*>).updateSelectedItemColorStateList()
        }
    }

    override fun setSimpleItemSelectedRippleColor(simpleItemSelectedRippleColor: ColorStateList?) {
        super.setSimpleItemSelectedRippleColor(simpleItemSelectedRippleColor)
        if (adapter is MaterialSearchArrayAdapter<*>) {
            (adapter as MaterialSearchArrayAdapter<*>).updateSelectedItemColorStateList()
        }
    }

    private inner class MaterialSearchArrayAdapter<T> constructor(
        context: Context,
        resource: Int,
        objects: Array<out String?>
    ) : ArrayAdapter<String?>(context, resource, objects) {
        private var selectedItemRippleOverlaidColor: ColorStateList? = null
        private var pressedRippleColor: ColorStateList? = null

        init {
            updateSelectedItemColorStateList()
        }

        fun updateSelectedItemColorStateList() {
            pressedRippleColor = sanitizeDropdownItemSelectedRippleColor()
            selectedItemRippleOverlaidColor = createItemSelectedColorStateList()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            if (view is TextView) {
                val isSelectedItem: Boolean = text.toString().contentEquals(view.text)
                ViewCompat.setBackground(view, if (isSelectedItem) selectedItemDrawable else null)
            }
            return view
        }

        private val selectedItemDrawable: Drawable?
            get() {
                if (!hasSelectedColor()) {
                    return null
                }
                val colorDrawable: Drawable = ColorDrawable(simpleItemSelectedColor)
                return if (pressedRippleColor != null) {
                    DrawableCompat.setTintList(colorDrawable, selectedItemRippleOverlaidColor)
                    RippleDrawable(pressedRippleColor!!, colorDrawable, null)
                } else {
                    colorDrawable
                }
            }

        private fun createItemSelectedColorStateList(): ColorStateList? {
            if (!hasSelectedColor() || !hasSelectedRippleColor()
            ) {
                return null
            }
            val stateHovered =
                intArrayOf(android.R.attr.state_hovered, -android.R.attr.state_pressed)
            val stateSelected =
                intArrayOf(android.R.attr.state_selected, -android.R.attr.state_pressed)
            val colorSelected: Int =
                simpleItemSelectedRippleColor!!.getColorForState(stateSelected, Color.TRANSPARENT)
            val colorHovered: Int =
                simpleItemSelectedRippleColor!!.getColorForState(stateHovered, Color.TRANSPARENT)
            val colors = intArrayOf(
                MaterialColors.layer(simpleItemSelectedColor, colorSelected),
                MaterialColors.layer(simpleItemSelectedColor, colorHovered),
                simpleItemSelectedColor
            )
            val states = arrayOf(stateSelected, stateHovered, intArrayOf())
            return ColorStateList(states, colors)
        }

        private fun sanitizeDropdownItemSelectedRippleColor(): ColorStateList? {
            if (!hasSelectedRippleColor()) {
                return null
            }
            val statePressed = intArrayOf(android.R.attr.state_pressed)
            val colors = intArrayOf(
                simpleItemSelectedRippleColor!!.getColorForState(statePressed, Color.TRANSPARENT),
                Color.TRANSPARENT
            )
            val states = arrayOf(statePressed, intArrayOf())
            return ColorStateList(states, colors)
        }

        private fun hasSelectedColor(): Boolean {
            return simpleItemSelectedColor != Color.TRANSPARENT
        }

        private fun hasSelectedRippleColor(): Boolean {
            return simpleItemSelectedRippleColor != null
        }

        override fun getFilter(): Filter {
            return super.getFilter()
        }
    }
}