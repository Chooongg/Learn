package com.chooongg.basic.ext

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.ComponentName
import android.content.Context
import android.view.Window
import android.widget.FrameLayout
import androidx.appcompat.widget.ContentFrameLayout
import androidx.fragment.app.Fragment

inline val Activity.decorView: FrameLayout get() = window.decorView as FrameLayout
inline val Activity.contentView: ContentFrameLayout get() = findViewById(Window.ID_ANDROID_CONTENT)

/**
 * 加载 Activity 下 Label 的字符串
 */
fun Fragment.loadActivityLabel() = activity?.loadActivityLabel()

/**
 * 加载 Activity 下 Label 的字符串
 */
fun Context.loadActivityLabel(): CharSequence {
    val activity = getActivity() ?: return ""
    val activityInfo =
        packageManager.getActivityInfo(ComponentName(activity, activity.javaClass), 0)
    return activityInfo.loadLabel(packageManager)
}

/**
 * Activity是否活动
 */
fun Activity?.isLive() = this != null && !isFinishing && !isDestroyed