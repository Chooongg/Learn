package com.chooongg.basic.ext

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.use
import androidx.fragment.app.Fragment

fun Context.attrText(@AttrRes id: Int) = obtainStyledAttributes(intArrayOf(id)).use {
    it.getText(0)
}

fun Context.attrString(@AttrRes id: Int) = obtainStyledAttributes(intArrayOf(id)).use {
    it.getString(0)
}

fun Context.attrString(@AttrRes id: Int, vararg format: Any?) =
    obtainStyledAttributes(intArrayOf(id)).use {
        it.getString(0)?.format(*format)
    }

fun Context.attrBoolean(@AttrRes id: Int, defValue: Boolean) =
    obtainStyledAttributes(intArrayOf(id)).use {
        it.getBoolean(0, defValue)
    }

fun Context.attrInt(@AttrRes id: Int, defValue: Int) = obtainStyledAttributes(intArrayOf(id)).use {
    it.getInt(0, defValue)
}

fun Context.attrFloat(@AttrRes id: Int, defValue: Float) =
    obtainStyledAttributes(intArrayOf(id)).use {
        it.getFloat(0, defValue)
    }

fun Context.attrColor(@AttrRes id: Int, @ColorInt defValue: Int = Color.GRAY) =
    obtainStyledAttributes(intArrayOf(id)).use {
        it.getColor(0, defValue)
    }

fun Context.attrColorStateList(@AttrRes id: Int) = obtainStyledAttributes(intArrayOf(id)).use {
    it.getColorStateList(0)
}

fun Context.attrInteger(@AttrRes id: Int, defValue: Int) =
    obtainStyledAttributes(intArrayOf(id)).use {
        it.getInteger(0, defValue)
    }

fun Context.attrDimension(@AttrRes id: Int, defValue: Float) =
    obtainStyledAttributes(intArrayOf(id)).use {
        it.getDimension(0, defValue)
    }

fun Context.attrDimensionPixelOffset(@AttrRes id: Int, defValue: Int) =
    obtainStyledAttributes(intArrayOf(id)).use {
        it.getDimensionPixelOffset(0, defValue)
    }


fun Context.attrDimensionPixelSize(@AttrRes id: Int, defValue: Int) =
    obtainStyledAttributes(intArrayOf(id)).use {
        it.getDimensionPixelSize(0, defValue)
    }

fun Context.attrResourcesId(@AttrRes id: Int, defValue: Int) =
    obtainStyledAttributes(intArrayOf(id)).use {
        it.getResourceId(0, defValue)
    }

fun Context.attrDrawable(@AttrRes id: Int) = obtainStyledAttributes(intArrayOf(id)).use {
    it.getDrawable(0)
}


fun Fragment.attrText(@AttrRes id: Int) = requireContext().attrText(id)

fun Fragment.attrString(@AttrRes id: Int) = requireContext().attrString(id)

fun Fragment.attrString(@AttrRes id: Int, vararg format: Any?) =
    requireContext().attrString(id, *format)

fun Fragment.attrBoolean(@AttrRes id: Int, defValue: Boolean) =
    requireContext().attrBoolean(id, defValue)

fun Fragment.attrInt(@AttrRes id: Int, defValue: Int) = requireContext().attrInt(id, defValue)

fun Fragment.attrFloat(@AttrRes id: Int, defValue: Float) = requireContext().attrFloat(id, defValue)

fun Fragment.attrColor(@AttrRes id: Int, @ColorInt defValue: Int = Color.GRAY) =
    requireContext().attrColor(id, defValue)

fun Fragment.attrColorStateList(@AttrRes id: Int) = requireContext().attrColorStateList(id)

fun Fragment.attrInteger(@AttrRes id: Int, defValue: Int) =
    requireContext().attrInteger(id, defValue)

fun Fragment.attrDimension(@AttrRes id: Int, defValue: Float) =
    requireContext().attrDimension(id, defValue)

fun Fragment.attrDimensionPixelOffset(@AttrRes id: Int, defValue: Int) =
    requireContext().attrDimensionPixelOffset(id, defValue)

fun Fragment.attrDimensionPixelSize(@AttrRes id: Int, defValue: Int) =
    requireContext().attrDimensionPixelSize(id, defValue)

fun Fragment.attrResourcesId(@AttrRes id: Int, defValue: Int) =
    requireContext().attrResourcesId(id, defValue)

fun Fragment.attrDrawable(@AttrRes id: Int) = requireContext().attrDrawable(id)


fun View.attrText(@AttrRes id: Int) = context.attrText(id)

fun View.attrString(@AttrRes id: Int) = context.attrString(id)

fun View.attrString(@AttrRes id: Int, vararg format: Any?) = context.attrString(id, *format)

fun View.attrBoolean(@AttrRes id: Int, defValue: Boolean) = context.attrBoolean(id, defValue)

fun View.attrInt(@AttrRes id: Int, defValue: Int) = context.attrInt(id, defValue)

fun View.attrFloat(@AttrRes id: Int, defValue: Float) = context.attrFloat(id, defValue)

fun View.attrColor(@AttrRes id: Int, @ColorInt defValue: Int = Color.GRAY) =
    context.attrColor(id, defValue)

fun View.attrColorStateList(@AttrRes id: Int) = context.attrColorStateList(id)

fun View.attrInteger(@AttrRes id: Int, defValue: Int) = context.attrInteger(id, defValue)

fun View.attrDimension(@AttrRes id: Int, defValue: Float) = context.attrDimension(id, defValue)

fun View.attrDimensionPixelOffset(@AttrRes id: Int, defValue: Int) =
    context.attrDimensionPixelOffset(id, defValue)

fun View.attrDimensionPixelSize(@AttrRes id: Int, defValue: Int) =
    context.attrDimensionPixelSize(id, defValue)

fun View.attrResourcesId(@AttrRes id: Int, defValue: Int) = context.attrResourcesId(id, defValue)

fun View.attrDrawable(@AttrRes id: Int) = context.attrDrawable(id)