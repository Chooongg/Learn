package com.chooongg.core.popupMenu

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.appcompat.view.ContextThemeWrapper

/**
 * Holds all the required information for showing a popup menu.
 *
 * @param style Style of the popup menu
 * @param sections a list of sections
 */
class LearnPopupMenu internal constructor(
    @StyleRes internal val style: Int,
    internal val sections: List<PopupMenuSection>,
) {

    private var popupWindow: LearnRecyclerViewPopupWindow? = null

    private var dismissListener: (() -> Unit)? = null

    var overlapAnchor: Boolean? = null

    var gravity: Int = Gravity.NO_GRAVITY
    var width: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    var verticalOffset: Int = 0
    var horizontalOffset: Int = 0
    var paddingStart: Int = 0
    var paddingEnd: Int = 0
    var paddingTop: Int = 0
    var paddingBottom: Int = 0

    /**
     * Shows a popup menu in the UI.
     *
     * This must be called on the UI thread.
     * @param context Context
     * @param anchor view used to anchor the popup
     */
    @UiThread
    fun show(context: Context, anchor: View) {
        val styledContext = if (style != 0) ContextThemeWrapper(context, style) else context

        val popupWindow = LearnRecyclerViewPopupWindow(styledContext, anchor).apply {
            overlapAnchor = this@LearnPopupMenu.overlapAnchor
            gravity = this@LearnPopupMenu.gravity
            width = this@LearnPopupMenu.width
            verticalOffset = this@LearnPopupMenu.verticalOffset
            horizontalOffset = this@LearnPopupMenu.horizontalOffset
            paddingStart = this@LearnPopupMenu.paddingStart
            paddingEnd = this@LearnPopupMenu.paddingEnd
            paddingTop = this@LearnPopupMenu.paddingTop
            paddingBottom = this@LearnPopupMenu.paddingBottom
        }
        val adapter = PopupMenuAdapter(sections) { popupWindow.dismiss() }
        popupWindow.adapter = adapter
        popupWindow.show()
        this.popupWindow = popupWindow
        setOnDismissListener(this.dismissListener)
    }

    /**
     * Dismisses the popup window.
     */
    @UiThread
    fun dismiss() {
        this.popupWindow?.dismiss()
    }

    /**
     * Sets a listener that is called when this popup window is dismissed.
     *
     * @param listener Listener that is called when this popup window is dismissed.
     */
    fun setOnDismissListener(listener: (() -> Unit)?) {
        this.dismissListener = listener
        this.popupWindow?.setOnDismissListener(listener)
    }

    internal data class PopupMenuSection(
        val title: CharSequence?,
        val items: List<AbstractPopupMenuItem>
    )

    internal data class PopupMenuItem(
        val label: CharSequence?,
        @StringRes val labelRes: Int,
        @ColorInt val labelColor: Int,
        @DrawableRes val icon: Int,
        val iconDrawable: Drawable?,
        @ColorInt val iconColor: Int,
        val hasNestedItems: Boolean,
        override val viewBoundCallback: ViewBoundCallback,
        override val callback: () -> Unit,
        override val dismissOnSelect: Boolean
    ) : AbstractPopupMenuItem(callback, dismissOnSelect, viewBoundCallback)

    internal data class PopupMenuCustomItem(
        @LayoutRes val layoutResId: Int,
        override val viewBoundCallback: ViewBoundCallback,
        override val callback: () -> Unit,
        override val dismissOnSelect: Boolean
    ) : AbstractPopupMenuItem(callback, dismissOnSelect, viewBoundCallback)

    internal abstract class AbstractPopupMenuItem(
        open val callback: () -> Unit,
        open val dismissOnSelect: Boolean,
        open val viewBoundCallback: ViewBoundCallback
    )
}
