package com.chooongg.core.popupMenu2

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.annotation.GravityInt
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat
import com.chooongg.core.popupMenu2.model.PopupMenuSection

class PopupMenuBuilder {

    @StyleRes
    var style: Int = ResourcesCompat.ID_NULL

    var overlapAnchor: Boolean = false
    var isForceShowIcon: Boolean = false

    @GravityInt
    var dropdownGravity: Int = Gravity.NO_GRAVITY
    var dropDownWidth: Int = LayoutParams.WRAP_CONTENT
    var dropDownVerticalOffset: Int = 0
    var dropDownHorizontalOffset: Int = 0

    private val sectionList: ArrayList<PopupMenuSection> = arrayListOf()

    fun section(block: PopupMenuSection.() -> Unit) {
        sectionList.add(PopupMenuSection().apply(block))
    }

    fun build() = PopupMenu(
        style,
        overlapAnchor,
        isForceShowIcon,
        dropdownGravity,
        dropDownWidth,
        dropDownVerticalOffset,
        dropDownHorizontalOffset,
        sectionList
    )

    fun show(context: Context, anchor: View) = build().show(context, anchor)
}