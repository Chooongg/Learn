package com.chooongg.core.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.webkit.WebView
import android.widget.OverScroller
import androidx.core.view.NestedScrollingChild3
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper.INVALID_POINTER
import kotlin.math.abs

class NestedWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.webViewStyle
) : WebView(context, attrs, defStyleAttr), NestedScrollingChild3 {

    private val mScrollOffset = IntArray(2)
    private val mScrollConsumed = IntArray(2)

    private var mLastMotionY = 0
    private val mChildHelper = NestedScrollingChildHelper(this)
    private var mIsBeingDragged = false
    private var mVelocityTracker: VelocityTracker? = null
    private var mTouchSlop = 0
    private var mActivePointerId = INVALID_POINTER
    private var mNestedYOffset = 0
    private var mScroller: OverScroller? = null
    private var mMinimumVelocity = 0
    private var mMaximumVelocity = 0
    private var mLastScrollerY = 0

    init {
        overScrollMode = OVER_SCROLL_NEVER
        initScrollView()
        isNestedScrollingEnabled = true
    }

    private fun initScrollView() {
        mScroller = OverScroller(context)
        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
        mMinimumVelocity = configuration.scaledMinimumFlingVelocity
        mMaximumVelocity = configuration.scaledMaximumFlingVelocity
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        if (action == MotionEvent.ACTION_MOVE && mIsBeingDragged) { // most common
            return true
        }
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_MOVE -> {
                val activePointerId = mActivePointerId
                if (activePointerId == INVALID_POINTER) {
                    return mIsBeingDragged
                }
                val pointerIndex = ev.findPointerIndex(activePointerId)
                if (pointerIndex == -1) {
                    return mIsBeingDragged
                }
                val y = ev.getY(pointerIndex).toInt()
                val yDiff = abs(y - mLastMotionY)
                if (yDiff > mTouchSlop && nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL == 0) {
                    mIsBeingDragged = true
                    mLastMotionY = y
                    initVelocityTrackerIfNotExists()
                    mVelocityTracker!!.addMovement(ev)
                    mNestedYOffset = 0
                    val parent = parent
                    parent?.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_DOWN -> {
                mLastMotionY = ev.y.toInt()
                mActivePointerId = ev.getPointerId(0)
                initOrResetVelocityTracker()
                mVelocityTracker!!.addMovement(ev)
                mScroller!!.computeScrollOffset()
                mIsBeingDragged = !mScroller!!.isFinished
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                mIsBeingDragged = false
                mActivePointerId = INVALID_POINTER
                recycleVelocityTracker()
                if (mScroller!!.springBack(scrollX, scrollY, 0, 0, 0, getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation(this)
                }
                stopNestedScroll()
            }
            MotionEvent.ACTION_POINTER_UP -> onSecondaryPointerUp(ev)
        }
        return mIsBeingDragged
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        initVelocityTrackerIfNotExists()
        val vtev = MotionEvent.obtain(ev)
        val actionMasked = ev.actionMasked
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            mNestedYOffset = 0
        }
        vtev.offsetLocation(0f, mNestedYOffset.toFloat())
        when (actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller!!.isFinished.also { mIsBeingDragged = it }) {
                    val parent = parent
                    parent?.requestDisallowInterceptTouchEvent(true)
                }
                if (!mScroller!!.isFinished) {
                    abortAnimatedScroll()
                }
                mLastMotionY = ev.y.toInt()
                mActivePointerId = ev.getPointerId(0)
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH)
            }
            MotionEvent.ACTION_MOVE -> {
                val activePointerIndex = ev.findPointerIndex(mActivePointerId)
                if (activePointerIndex != -1) {
                    val y = ev.getY(activePointerIndex).toInt()
                    var deltaY = mLastMotionY - y
                    if (dispatchNestedPreScroll(
                            0, deltaY, mScrollConsumed, mScrollOffset,
                            ViewCompat.TYPE_TOUCH
                        )
                    ) {
                        deltaY -= mScrollConsumed[1]
                        mNestedYOffset += mScrollOffset[1]
                    }
                    if (!mIsBeingDragged && abs(deltaY) > mTouchSlop) {
                        val parent = parent
                        parent?.requestDisallowInterceptTouchEvent(true)
                        mIsBeingDragged = true
                        if (deltaY > 0) {
                            deltaY -= mTouchSlop
                        } else {
                            deltaY += mTouchSlop
                        }
                    }
                    if (mIsBeingDragged) {
                        mLastMotionY = y - mScrollOffset[1]
                        val oldY = scrollY
                        val range = getScrollRange()

                        // Calling overScrollByCompat will call onOverScrolled, which
                        // calls onScrollChanged if applicable.
                        if (overScrollByCompat(
                                0, deltaY, 0, oldY, 0, range, 0, 0
                            ) && !hasNestedScrollingParent(ViewCompat.TYPE_TOUCH)
                        ) {
                            mVelocityTracker!!.clear()
                        }
                        val scrolledDeltaY = scrollY - oldY
                        val unconsumedY = deltaY - scrolledDeltaY
                        mScrollConsumed[1] = 0
                        dispatchNestedScroll(
                            0, scrolledDeltaY, 0, unconsumedY, mScrollOffset,
                            ViewCompat.TYPE_TOUCH, mScrollConsumed
                        )
                        mLastMotionY -= mScrollOffset[1]
                        mNestedYOffset += mScrollOffset[1]
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                val velocityTracker = mVelocityTracker!!
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity.toFloat())
                val initialVelocity = velocityTracker.getYVelocity(mActivePointerId).toInt()
                if (abs(initialVelocity) > mMinimumVelocity) {
                    if (!dispatchNestedPreFling(0f, -initialVelocity.toFloat())) {
                        dispatchNestedFling(0f, -initialVelocity.toFloat(), true)
                        fling(-initialVelocity)
                    }
                } else if (mScroller!!.springBack(
                        scrollX, scrollY, 0, 0, 0,
                        getScrollRange()
                    )
                ) {
                    ViewCompat.postInvalidateOnAnimation(this)
                }
                mActivePointerId = INVALID_POINTER
                endDrag()
            }
            MotionEvent.ACTION_CANCEL -> {
                if (mIsBeingDragged) {
                    if (mScroller!!.springBack(
                            scrollX, scrollY, 0, 0, 0,
                            getScrollRange()
                        )
                    ) {
                        ViewCompat.postInvalidateOnAnimation(this)
                    }
                }
                mActivePointerId = INVALID_POINTER
                endDrag()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val index = ev.actionIndex
                mLastMotionY = ev.getY(index).toInt()
                mActivePointerId = ev.getPointerId(index)
            }
            MotionEvent.ACTION_POINTER_UP -> {
                onSecondaryPointerUp(ev)
                mLastMotionY = ev.getY(ev.findPointerIndex(mActivePointerId)).toInt()
            }
        }
        mVelocityTracker?.addMovement(vtev)
        vtev.recycle()
        return super.onTouchEvent(ev)
    }

    private fun abortAnimatedScroll() {
        mScroller!!.abortAnimation()
        stopNestedScroll(ViewCompat.TYPE_NON_TOUCH)
    }

    private fun endDrag() {
        mIsBeingDragged = false
        recycleVelocityTracker()
        stopNestedScroll()
    }

    private fun onSecondaryPointerUp(ev: MotionEvent) {
        val pointerIndex = (ev.action and MotionEvent.ACTION_POINTER_INDEX_MASK
                shr MotionEvent.ACTION_POINTER_INDEX_SHIFT)
        val pointerId = ev.getPointerId(pointerIndex)
        if (pointerId == mActivePointerId) {
            val newPointerIndex = if (pointerIndex == 0) 1 else 0
            mLastMotionY = ev.getY(newPointerIndex).toInt()
            mActivePointerId = ev.getPointerId(newPointerIndex)
            mVelocityTracker?.clear()
        }
    }

    private fun fling(velocityY: Int) {
        val height = height
        mScroller!!.fling(
            scrollX, scrollY,  // start
            0, velocityY,  // velocities
            0, 0, Int.MIN_VALUE, Int.MAX_VALUE,  // y
            0, height / 2
        )
        runAnimatedScroll(true)
    }

    private fun runAnimatedScroll(participateInNestedScrolling: Boolean) {
        if (participateInNestedScrolling) {
            startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_NON_TOUCH)
        } else {
            stopNestedScroll(ViewCompat.TYPE_NON_TOUCH)
        }
        mLastScrollerY = scrollY
        ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        if (disallowIntercept) {
            recycleVelocityTracker()
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    private fun initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        } else mVelocityTracker!!.clear()
    }

    private fun initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
    }

    private fun recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker!!.recycle()
            mVelocityTracker = null
        }
    }

    override fun overScrollBy(
        deltaX: Int, deltaY: Int,
        scrollX: Int, scrollY: Int,
        scrollRangeX: Int, scrollRangeY: Int,
        maxOverScrollX: Int, maxOverScrollY: Int,
        isTouchEvent: Boolean
    ): Boolean {
        // this is causing double scroll call (doubled speed), but this WebView isn't overscrollable
        // all overscrolls are passed to appbar, so commenting this out during drag
        if (!mIsBeingDragged) overScrollByCompat(
            deltaX,
            deltaY,
            scrollX,
            scrollY,
            scrollRangeX,
            scrollRangeY,
            maxOverScrollX,
            maxOverScrollY
        )
        // without this call webview won't scroll to top when url change or when user pick input
        // (webview should move a bit making input still in viewport when "adjustResize")
        return true
    }

    fun getScrollRange(): Int {
        //Using scroll range of webview instead of childs as NestedScrollView does.
        return computeVerticalScrollRange()
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mChildHelper.isNestedScrollingEnabled
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        return mChildHelper.startNestedScroll(axes, type)
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return startNestedScroll(axes, ViewCompat.TYPE_TOUCH)
    }

    override fun stopNestedScroll(type: Int) {
        mChildHelper.stopNestedScroll(type)
    }

    override fun stopNestedScroll() {
        stopNestedScroll(ViewCompat.TYPE_TOUCH)
    }

    override fun hasNestedScrollingParent(type: Int): Boolean {
        return mChildHelper.hasNestedScrollingParent(type)
    }

    override fun hasNestedScrollingParent(): Boolean {
        return hasNestedScrollingParent(ViewCompat.TYPE_TOUCH)
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {
        mChildHelper.dispatchNestedScroll(
            dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed
        )
    }


    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ) = dispatchNestedScroll(
        dxConsumed,
        dyConsumed,
        dxUnconsumed,
        dyUnconsumed,
        offsetInWindow,
        ViewCompat.TYPE_TOUCH
    )

    override fun dispatchNestedScroll(
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int,
        offsetInWindow: IntArray?, type: Int
    ) = mChildHelper.dispatchNestedScroll(
        dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type
    )

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ) = dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, ViewCompat.TYPE_TOUCH)


    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, false)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun getNestedScrollAxes(): Int {
        return ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun computeScroll() {
        if (mScroller!!.isFinished) {
            return
        }
        mScroller!!.computeScrollOffset()
        val y = mScroller!!.currY
        var unconsumed = y - mLastScrollerY
        mLastScrollerY = y

        // Nested Scrolling Pre Pass
        mScrollConsumed[1] = 0
        dispatchNestedPreScroll(
            0, unconsumed, mScrollConsumed, null,
            ViewCompat.TYPE_NON_TOUCH
        )
        unconsumed -= mScrollConsumed[1]
        if (unconsumed != 0) {
            // Internal Scroll
            val oldScrollY = scrollY
            overScrollByCompat(
                0, unconsumed, scrollX, oldScrollY, 0, getScrollRange(), 0, 0
            )
            val scrolledByMe = scrollY - oldScrollY
            unconsumed -= scrolledByMe

            // Nested Scrolling Post Pass
            mScrollConsumed[1] = 0
            dispatchNestedScroll(
                0, 0, 0, unconsumed, mScrollOffset,
                ViewCompat.TYPE_NON_TOUCH, mScrollConsumed
            )
            unconsumed -= mScrollConsumed[1]
        }
        if (unconsumed != 0) {
            abortAnimatedScroll()
        }
        if (!mScroller!!.isFinished) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    // copied from NestedScrollView exacly as it looks, leaving overscroll related code, maybe future use
    private fun overScrollByCompat(
        deltaX: Int, deltaY: Int,
        scrollX: Int, scrollY: Int,
        scrollRangeX: Int, scrollRangeY: Int,
        maxOverScrollX: Int, maxOverScrollY: Int
    ): Boolean {
        var maxOverScrollX1 = maxOverScrollX
        var maxOverScrollY1 = maxOverScrollY
        val overScrollMode = overScrollMode
        val canScrollHorizontal = computeHorizontalScrollRange() > computeHorizontalScrollExtent()
        val canScrollVertical = computeVerticalScrollRange() > computeVerticalScrollExtent()
        val overScrollHorizontal = (overScrollMode == View.OVER_SCROLL_ALWAYS
                || overScrollMode == View.OVER_SCROLL_IF_CONTENT_SCROLLS && canScrollHorizontal)
        val overScrollVertical = (overScrollMode == View.OVER_SCROLL_ALWAYS
                || overScrollMode == View.OVER_SCROLL_IF_CONTENT_SCROLLS && canScrollVertical)
        var newScrollX = scrollX + deltaX
        if (!overScrollHorizontal) {
            maxOverScrollX1 = 0
        }
        var newScrollY = scrollY + deltaY
        if (!overScrollVertical) {
            maxOverScrollY1 = 0
        }

        // Clamp values if at the limits and record
        val left = -maxOverScrollX1
        val right = maxOverScrollX1 + scrollRangeX
        val top = -maxOverScrollY1
        val bottom = maxOverScrollY1 + scrollRangeY
        var clampedX = false
        if (newScrollX > right) {
            newScrollX = right
            clampedX = true
        } else if (newScrollX < left) {
            newScrollX = left
            clampedX = true
        }
        var clampedY = false
        if (newScrollY > bottom) {
            newScrollY = bottom
            clampedY = true
        } else if (newScrollY < top) {
            newScrollY = top
            clampedY = true
        }
        if (clampedY && !hasNestedScrollingParent(ViewCompat.TYPE_NON_TOUCH)) {
            mScroller!!.springBack(newScrollX, newScrollY, 0, 0, 0, getScrollRange())
        }
        onOverScrolled(newScrollX, newScrollY, clampedX, clampedY)
        return clampedX || clampedY
    }
}