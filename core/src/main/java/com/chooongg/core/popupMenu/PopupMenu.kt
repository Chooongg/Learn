package com.chooongg.core.popupMenu

import android.content.Context
import android.view.View
import androidx.annotation.GravityInt
import androidx.annotation.StyleRes
import androidx.annotation.UiThread
import androidx.appcompat.view.ContextThemeWrapper
import com.chooongg.core.popupMenu.model.PopupMenuSection

class PopupMenu internal constructor(
    @StyleRes internal val style: Int,
    var overlapAnchor: Boolean,
    var isForceShowIcon: Boolean,
    @GravityInt var dropdownGravity: Int,
    var dropDownWidth: Int,
    var dropDownVerticalOffset: Int,
    var dropDownHorizontalOffset: Int,
    private val sectionList: ArrayList<PopupMenuSection>
) {

    private var popupWindow: LearnRecyclerViewPopupWindow? = null

    private var dismissListener: (() -> Unit)? = null

    @UiThread
    fun show(context: Context, anchor: View) {
        val styledContext = if (style != 0) ContextThemeWrapper(context, style) else context
        val popupWindow = LearnRecyclerViewPopupWindow(
            styledContext,
            anchor,
            overlapAnchor,
            dropdownGravity,
            dropDownWidth,
            dropDownVerticalOffset,
            dropDownHorizontalOffset
        )
        val adapter = PopupMenuAdapter(isForceShowIcon,sectionList) { popupWindow.dismiss() }
        popupWindow.adapter = adapter
        popupWindow.show()
        this.popupWindow = popupWindow
        setOnDismissListener(this.dismissListener)
    }

    @UiThread
    fun dismiss() {
        this.popupWindow?.dismiss()
    }

    fun setOnDismissListener(listener: (() -> Unit)?) {
        this.dismissListener = listener
        this.popupWindow?.setOnDismissListener(listener)
    }
}