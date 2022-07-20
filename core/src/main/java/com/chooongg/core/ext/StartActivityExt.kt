package com.chooongg.core.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.chooongg.basic.ACTIVITY_TOP
import com.chooongg.basic.APPLICATION
import kotlin.reflect.KClass

internal const val EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME"

fun startActivity(
    clazz: KClass<out Activity>,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(clazz, getActivityOption(ACTIVITY_TOP)?.toBundle(), block)
}

fun startActivity(
    clazz: KClass<out Activity>,
    vararg sharedElements: Pair<View, String>,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(clazz, getActivityOption(ACTIVITY_TOP, *sharedElements)?.toBundle(), block)
}

fun startActivity(
    clazz: KClass<out Activity>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(
        clazz,
        getActivityOption(ACTIVITY_TOP, Pair.create(view, "transitions_to_content"))?.toBundle(),
    ) {
        if (ACTIVITY_TOP != null) putExtra(EXTRA_TRANSITION_NAME, "transitions_to_content")
        block?.invoke(this)
    }
}

fun startActivity(
    clazz: KClass<out Activity>,
    option: Bundle?,
    block: (Intent.() -> Unit)? = null
) {
    val context = ACTIVITY_TOP ?: APPLICATION
    val intent = Intent(context, clazz.java)
    block?.invoke(intent)
    if (ACTIVITY_TOP == null) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent, option)
}

private fun getActivityOption(
    activity: Activity?,
    vararg sharedElements: Pair<View, String>
) = if (activity != null) {
    ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *sharedElements)
} else null