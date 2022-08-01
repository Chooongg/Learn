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
import com.chooongg.basic.ext.logE
import kotlin.reflect.KClass

internal const val EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME"

fun Context.startActivity(
    clazz: KClass<out Activity>,
    vararg sharedElements: Pair<View, String>,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(
        this, clazz,
        getActivityOption(this.getActivity(), *sharedElements)?.toBundle(),
        block
    )
}

fun Context.startActivity(
    clazz: KClass<out Activity>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(
        this, clazz,
        getActivityOption(
            this.getActivity(), Pair.create(view, "transitions_to_content")
        )?.toBundle(),
    ) {
        putExtra(EXTRA_TRANSITION_NAME, "transitions_to_content")
        block?.invoke(this)
    }
}

fun Fragment.startActivity(
    clazz: KClass<out Activity>,
    vararg sharedElements: Pair<View, String>,
    block: (Intent.() -> Unit)? = null
) {
    requireContext().startActivity(clazz, *sharedElements, block)
}

fun Fragment.startActivity(
    clazz: KClass<out Activity>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    requireContext().startActivity(clazz, view, block)
}

fun startActivity(
    context: Context?,
    clazz: KClass<out Activity>,
    option: Bundle?,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(context, clazz.java)
    block?.invoke(intent)
    if (context == null) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    (context ?: APPLICATION).startActivity(intent, option)
    logE("启动Activity")
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