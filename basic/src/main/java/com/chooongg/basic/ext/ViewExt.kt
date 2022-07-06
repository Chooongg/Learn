package com.chooongg.basic.ext

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View

val View?.localVisibleRect: Rect get() = Rect().also { this?.getLocalVisibleRect(it) }
val View?.globalVisibleRect: Rect get() = Rect().also { this?.getGlobalVisibleRect(it) }
val View?.isRectVisible: Boolean get() = this != null && globalVisibleRect != localVisibleRect
val View?.isVisible: Boolean get() = this != null && visibility == View.VISIBLE
fun View.visible() = apply { if (visibility != View.VISIBLE) visibility = View.VISIBLE }
fun View.inVisible() = apply { if (visibility != View.INVISIBLE) visibility = View.INVISIBLE }
fun View.gone() = apply { if (visibility != View.GONE) visibility = View.GONE }

const val CLICK_INTERVAL = 600L

private var lastClickTime = 0L

/**
 * 点击效验 防止快速双重点击
 */
fun multipleValid(): Boolean {
    val currentTime = System.currentTimeMillis()
    return if (currentTime - lastClickTime > CLICK_INTERVAL) {
        lastClickTime = currentTime
        true
    } else false
}

fun View.doOnClick(block: (View) -> Unit) = apply {
    setOnClickListener { if (multipleValid()) block(this) }
}

fun View.doOnLongClick(block: (View) -> Boolean) = apply {
    setOnLongClickListener { if (multipleValid()) block(it) else false }
}

/**
 * 批量设置点击事件
 */
fun setOnClicks(vararg views: View, block: (View) -> Unit) {
    views.forEach { it.doOnClick(block) }
}

/**
 * 批量设置长按事件
 */
fun setOnLongClicks(vararg views: View, block: (View) -> Boolean) {
    views.forEach { it.doOnLongClick(block) }
}

inline fun <T : View> T.postApply(crossinline block: T.() -> Unit) {
    post { apply(block) }
}

inline fun <T : View> T.postDelayed(delayMillis: Long, crossinline block: T.() -> Unit) {
    postDelayed({ block() }, delayMillis)
}

/**
 * View截图返回Bitmap
 */
fun View.toBitmap(config: Bitmap.Config = Bitmap.Config.RGB_565): Bitmap {
    val screenshot = Bitmap.createBitmap(width, height, config)
    val canvas = Canvas(screenshot)
    canvas.translate(-scrollX.toFloat(), -scrollY.toFloat())
    draw(canvas)
    return screenshot
}

fun View?.isTouchView(event: MotionEvent?): Boolean {
    if (event == null || this == null) return false
    val x = event.x
    val y = event.y
    val outLocation = IntArray(2)
    getLocationOnScreen(outLocation)
    val rectF = RectF(
        outLocation[0].toFloat(),
        outLocation[1].toFloat(),
        outLocation[0].toFloat() + width,
        outLocation[1].toFloat() + height
    )
    return x >= rectF.left && x <= rectF.right && y >= rectF.top && y <= rectF.bottom
}