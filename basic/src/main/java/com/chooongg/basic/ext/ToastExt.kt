package com.chooongg.basic.ext

import android.view.Gravity
import android.widget.Toast
import androidx.annotation.GravityInt
import androidx.annotation.StringRes
import com.chooongg.basic.ACTIVITY_TOP
import com.chooongg.basic.APPLICATION

/**
 * Toast 唯一实例
 */
private var basicToast: Toast? = null

/**
 * 展示 Toast
 */
fun showToast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    cancelToast()
    basicToast = Toast.makeText(ACTIVITY_TOP ?: APPLICATION, text, duration).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

/**
 * 展示 Toast
 */
fun showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    cancelToast()
    basicToast = Toast.makeText(ACTIVITY_TOP ?: APPLICATION, resId, duration).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

/**
 * 展示 Toast
 */
fun showToast(
    text: CharSequence?,
    @GravityInt gravity: Int,
    xOffset: Int = 0,
    yOffset: Int = 0,
    duration: Int = Toast.LENGTH_SHORT
) {
    cancelToast()
    basicToast = Toast.makeText(ACTIVITY_TOP ?: APPLICATION, text, duration).apply {
        setGravity(gravity, xOffset, yOffset)
        show()
    }
}

/**
 * 展示 Toast
 */
fun showToast(
    @StringRes resId: Int,
    @GravityInt gravity: Int,
    xOffset: Int = 0,
    yOffset: Int = 0,
    duration: Int = Toast.LENGTH_SHORT
) {
    cancelToast()
    basicToast = Toast.makeText(ACTIVITY_TOP ?: APPLICATION, resId, duration).apply {
        setGravity(gravity, xOffset, yOffset)
        show()
    }
}


/**
 * 取消Toast
 */
fun cancelToast() {
    basicToast?.cancel()
    basicToast = null
}