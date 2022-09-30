package com.chooongg.core.popupMenu2

import androidx.annotation.GravityInt
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat
import com.chooongg.core.popupMenu2.model.PopupMenuSection

class PopupMenu internal constructor(private val sectionList: ArrayList<PopupMenuSection>) {

    @StyleRes
    internal var style: Int = ResourcesCompat.ID_NULL

    @GravityInt
    internal var dropdownGravity: Int? = null

    internal var overlapAnchor: Boolean? = null

    @androidx.annotation.IntRange(from = -2)
    internal var dropDownWidth: Int? = null

    internal var dropDownVerticalOffset: Int = 0
    internal var dropDownHorizontalOffset: Int = 0

    internal var dropdownPaddingStart: Int = 0
    internal var dropdownPaddingTop: Int = 0
    internal var dropdownPaddingEnd: Int = 0
    internal var dropdownPaddingBottom: Int = 0

}