package com.chooongg.core.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.chooongg.basic.APPLICATION
import com.chooongg.basic.ext.getActivity
import kotlin.reflect.KClass

internal const val EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME"

private fun Activity?.startActivity(
    clazz: KClass<out Activity>,
    options: Bundle?,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(this, clazz.java)
    block?.invoke(intent)
    if (this == null) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        APPLICATION.startActivity(intent, options)
    } else startActivity(intent, options)
}

fun Context?.startActivity(
    clazz: KClass<out Activity>,
    options: Bundle?,
    block: (Intent.() -> Unit)? = null
) {
    this?.getActivity().startActivity(clazz, options, block)
}

fun Context?.startActivity(
    clazz: KClass<out Activity>,
    vararg sharedElements: Pair<View, String>,
    block: (Intent.() -> Unit)? = null
) {
    val activity = this?.getActivity()
    activity.startActivity(clazz, getActivityOption(activity, *sharedElements)?.toBundle(), block)
}

fun Context?.startActivity(
    clazz: KClass<out Activity>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    val activity = this?.getActivity()
    activity.startActivity(
        clazz, getActivityOption(activity, Pair.create(view, "transitions_to_content"))?.toBundle()
    ) {
        putExtra(EXTRA_TRANSITION_NAME, "transitions_to_content")
        block?.invoke(this)
    }
}

fun Fragment.startActivity(
    clazz: KClass<out Activity>,
    options: Bundle?,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(context, clazz.java)
    block?.invoke(intent)
    if (activity == null) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        APPLICATION.startActivity(intent, options)
    } else startActivity(intent, options)
}

fun Fragment.startActivity(
    clazz: KClass<out Activity>,
    vararg sharedElements: Pair<View, String>,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(clazz, getActivityOption(activity, *sharedElements)?.toBundle(), block)
}

fun Fragment.startActivity(
    clazz: KClass<out Activity>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(
        clazz, getActivityOption(activity, Pair.create(view, "transitions_to_content"))?.toBundle()
    ) {
        putExtra(EXTRA_TRANSITION_NAME, "transitions_to_content")
        block?.invoke(this)
    }
}

private fun getActivityOption(
    activity: Activity?,
    vararg sharedElements: Pair<View, String>
) = if (activity != null) {
    when (sharedElements.size) {
        1 -> ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity, sharedElements[0].first, sharedElements[0].second
        )
        else -> ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *sharedElements)
    }
} else null