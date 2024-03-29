package com.chooongg.core.popupMenu

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.annotation.GravityInt
import androidx.core.widget.PopupWindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.dp2px
import kotlin.math.ceil

internal class LearnRecyclerViewPopupWindow(
    private val context: Context,
    private val anchorView: View,
    val overlapAnchor: Boolean,
    @GravityInt var gravity: Int,
    var width: Int,
    val verticalOffset: Int,
    val horizontalOffset: Int,
) {
    private val popupMaxWidth: Int = dp2px(280f)
    private val popupMinWidth: Int = dp2px(112f)
    private val popupWidthUnit: Int = dp2px(56f)

    private val popup: PopupWindow =
        PopupWindow(context, null, androidx.appcompat.R.attr.popupMenuStyle)

    private val tempRect = Rect()

    internal var adapter: PopupMenuAdapter? = null
        set(value) {
            if (width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                width = measureMenuSizeAndGetWidth(checkNotNull(value))
            }
            field = value
        }

    init {
        popup.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
        popup.isFocusable = true
    }

    internal fun show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.overlapAnchor = overlapAnchor
        }
        val height = buildDropDown()
        PopupWindowCompat.setWindowLayoutType(
            popup,
            WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL
        )
        val widthSpec = width
        if (popup.isShowing) {
            popup.isOutsideTouchable = true
            popup.update(
                anchorView, horizontalOffset,
                verticalOffset, widthSpec,
                if (height < 0) -1 else height
            )
        } else {
            popup.width = widthSpec
            popup.height = height
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popup.setIsClippedToScreen(true)
            }
            // use outside touchable to dismiss drop down when touching outside of it, so
            // only set this if the dropdown is not always visible
            popup.isOutsideTouchable = true
            PopupWindowCompat.showAsDropDown(
                popup, anchorView, horizontalOffset, verticalOffset, gravity
            )
        }
    }

    /**
     * Dismiss the popupMenu window.
     */
    fun dismiss() {
        popup.dismiss()
        popup.contentView = null
    }

    internal fun setOnDismissListener(listener: (() -> Unit)?) {
        popup.setOnDismissListener(listener)
    }

    private fun buildDropDown(): Int {
        var otherHeights = 0
        val dropDownList = RecyclerView(context).apply {
            elevation = popup.elevation
            overScrollMode = View.OVER_SCROLL_NEVER
            adapter = this@LearnRecyclerViewPopupWindow.adapter
            layoutManager = LinearLayoutManager(context)
            isFocusable = true
            isFocusableInTouchMode = true
        }
        dropDownList.clipToOutline = true
        popup.contentView = dropDownList
        // Max height available on the screen for a popupMenu.
        val ignoreBottomDecorations = popup.inputMethodMode == PopupWindow.INPUT_METHOD_NOT_NEEDED
        val maxHeight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            popup.getMaxAvailableHeight(
                anchorView, verticalOffset, ignoreBottomDecorations
            )
        } else {
            popup.getMaxAvailableHeight(anchorView, verticalOffset)
        }
        if (popup.background != null) {
            popup.background.getPadding(tempRect)
            otherHeights += tempRect.top + tempRect.bottom
        } else {
            tempRect.setEmpty()
        }
        val listContent = measureHeightOfChildrenCompat(maxHeight - otherHeights)
        if (listContent > 0) {
            val listPadding = dropDownList.paddingTop + dropDownList.paddingBottom
            otherHeights += listPadding
        }
        return listContent + otherHeights
    }

    private fun measureHeightOfChildrenCompat(maxHeight: Int): Int {
        val parent = FrameLayout(context)
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        // Include the padding of the list
        var returnedHeight = 0
        val count = adapter?.itemCount ?: 0
        for (i in 0 until count) {
            val positionType = adapter!!.getItemViewType(i)
            val vh = adapter!!.createViewHolder(parent, positionType)
            adapter!!.bindViewHolder(vh, i)
            val itemView = vh.itemView
            // Compute child height spec
            val heightMeasureSpec: Int
            var childLp: ViewGroup.LayoutParams? = itemView.layoutParams
            if (childLp == null) {
                childLp = generateDefaultLayoutParams()
                itemView.layoutParams = childLp
            }
            heightMeasureSpec = if (childLp.height > 0) {
                View.MeasureSpec.makeMeasureSpec(
                    childLp.height,
                    View.MeasureSpec.EXACTLY
                )
            } else {
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            }
            itemView.measure(widthMeasureSpec, heightMeasureSpec)
            // Since this view was measured directly against the parent measure
            // spec, we must measure it again before reuse.
            itemView.forceLayout()
            val marginLayoutParams = childLp as? ViewGroup.MarginLayoutParams
            val topMargin = marginLayoutParams?.topMargin ?: 0
            val bottomMargin = marginLayoutParams?.bottomMargin ?: 0
            val verticalMargin = topMargin + bottomMargin
            returnedHeight += itemView.measuredHeight + verticalMargin
            if (returnedHeight >= maxHeight) {
                // We went over, figure out which height to return.  If returnedHeight >
                // maxHeight, then the i'th position did not fit completely.
                return maxHeight
            }
        }
        // At this point, we went through the range of children, and they each
        // completely fit, so return the returnedHeight
        return returnedHeight
    }

    private fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun measureMenuSizeAndGetWidth(adapter: PopupMenuAdapter): Int {
        val parent = FrameLayout(context)
        var menuWidth = popupMinWidth
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val count = adapter.itemCount
        for (i in 0 until count) {
            val positionType = adapter.getItemViewType(i)
            val vh = adapter.createViewHolder(parent, positionType)
            adapter.bindViewHolder(vh, i)
            val itemView = vh.itemView
            itemView.measure(widthMeasureSpec, heightMeasureSpec)
            val itemWidth = itemView.measuredWidth
            if (itemWidth >= popupMaxWidth) {
                return popupMaxWidth
            } else if (itemWidth > menuWidth) {
                menuWidth = itemWidth
            }
        }
        menuWidth = ceil(menuWidth.toDouble() / popupWidthUnit).toInt() * popupWidthUnit
        return menuWidth
    }
}