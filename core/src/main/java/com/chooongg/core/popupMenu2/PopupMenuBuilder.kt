package com.chooongg.core.popupMenu2

import androidx.annotation.GravityInt
import androidx.annotation.StyleRes
import androidx.core.content.res.ResourcesCompat
import com.chooongg.core.popupMenu2.model.PopupMenuSection

class PopupMenuBuilder {

    @StyleRes
    var style: Int = ResourcesCompat.ID_NULL

    @GravityInt
    var dropdownGravity: Int? = null

    var overlapAnchor: Boolean? = null

    @androidx.annotation.IntRange(from = -2)
    var dropDownWidth: Int? = null

    var dropDownVerticalOffset: Int = 0
    var dropDownHorizontalOffset: Int = 0

    var dropdownPaddingStart: Int = 0
    var dropdownPaddingTop: Int = 0
    var dropdownPaddingEnd: Int = 0
    var dropdownPaddingBottom: Int = 0

    private val sectionList: ArrayList<PopupMenuSection> = arrayListOf()

    fun section(block: PopupMenuSection.() -> Unit) {
        sectionList.add(PopupMenuSection().apply(block))
    }

    fun build(): PopupMenu {
        val popupMenu = PopupMenu(sectionList)
        popupMenu.style = style
        popupMenu.dropdownGravity = dropdownGravity
        popupMenu.overlapAnchor = overlapAnchor
        popupMenu.dropDownWidth = dropDownWidth
        popupMenu.dropDownVerticalOffset = dropDownVerticalOffset
        popupMenu.dropDownHorizontalOffset = dropDownHorizontalOffset
        popupMenu.dropdownPaddingStart = dropdownPaddingStart
        popupMenu.dropdownPaddingTop = dropdownPaddingTop
        popupMenu.dropdownPaddingEnd = dropdownPaddingEnd
        popupMenu.dropdownPaddingBottom = dropdownPaddingBottom
        return popupMenu
    }
}