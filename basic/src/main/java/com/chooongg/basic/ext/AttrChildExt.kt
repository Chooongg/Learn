package com.chooongg.basic.ext

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.use
import androidx.fragment.app.Fragment

fun Context.attrChildText(@AttrRes id: Int, @AttrRes childId: Int) = with(TypedValue()) {
    theme.resolveAttribute(id, this, true)
    obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getText(0) }
}

fun Context.attrChildString(@AttrRes id: Int, @AttrRes childId: Int) = with(TypedValue()) {
    theme.resolveAttribute(id, this, true)
    obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getString(0) }
}

fun Context.attrChildString(@AttrRes id: Int, @AttrRes childId: Int, vararg format: Any?) =
    with(TypedValue()) {
        theme.resolveAttribute(id, this, true)
        obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getString(0)?.format(*format) }
    }

fun Context.attrChildBoolean(@AttrRes id: Int, @AttrRes childId: Int, defValue: Boolean) =
    with(TypedValue()) {
        theme.resolveAttribute(id, this, true)
        obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getBoolean(0, defValue) }
    }

fun Context.attrChildInt(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    with(TypedValue()) {
        theme.resolveAttribute(id, this, true)
        obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getInt(0, defValue) }
    }

fun Context.attrChildFloat(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    with(TypedValue()) {
        theme.resolveAttribute(id, this, true)
        obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getFloat(0, defValue) }
    }

fun Context.attrChildColor(
    @AttrRes id: Int, @AttrRes childId: Int, @ColorInt defValue: Int = Color.GRAY
) = with(TypedValue()) {
    theme.resolveAttribute(id, this, true)
    obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getColor(0, defValue) }
}

fun Context.attrChildColorStateList(@AttrRes id: Int, @AttrRes childId: Int) = with(TypedValue()) {
    theme.resolveAttribute(id, this, true)
    obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getColorStateList(0) }
}

fun Context.attrChildInteger(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    with(TypedValue()) {
        theme.resolveAttribute(id, this, true)
        obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getInteger(0, defValue) }
    }

fun Context.attrChildDimension(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    with(TypedValue()) {
        theme.resolveAttribute(id, this, true)
        obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getDimension(0, defValue) }
    }

fun Context.attrChildDimensionPixelOffset(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    with(TypedValue()) {
        theme.resolveAttribute(id, this, true)
        obtainStyledAttributes(resourceId, intArrayOf(id)).use {
            it.getDimensionPixelOffset(0, defValue)
        }
    }

fun Context.attrChildDimensionPixelSize(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    with(TypedValue()) {
        theme.resolveAttribute(id, this, true)
        obtainStyledAttributes(resourceId, intArrayOf(id)).use {
            it.getDimensionPixelSize(0, defValue)
        }
    }

fun Context.attrChildResourcesId(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    with(TypedValue()) {
        theme.resolveAttribute(id, this, true)
        obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getResourceId(0, defValue) }
    }

fun Context.attrChildDrawable(@AttrRes id: Int, @AttrRes childId: Int) = with(TypedValue()) {
    theme.resolveAttribute(id, this, true)
    obtainStyledAttributes(resourceId, intArrayOf(id)).use { it.getDrawable(0) }
}


fun Fragment.attrChildText(@AttrRes id: Int, @AttrRes childId: Int) =
    requireContext().attrChildText(id, childId)

fun Fragment.attrChildString(@AttrRes id: Int, @AttrRes childId: Int) =
    requireContext().attrChildString(id, childId)

fun Fragment.attrChildString(@AttrRes id: Int, @AttrRes childId: Int, vararg format: Any?) =
    requireContext().attrChildString(id, childId, *format)

fun Fragment.attrChildBoolean(@AttrRes id: Int, @AttrRes childId: Int, defValue: Boolean) =
    requireContext().attrChildBoolean(id, childId, defValue)

fun Fragment.attrChildInt(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildInt(id, childId, defValue)

fun Fragment.attrChildFloat(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    requireContext().attrChildFloat(id, childId, defValue)

fun Fragment.attrChildColor(
    @AttrRes id: Int, @AttrRes childId: Int, @ColorInt defValue: Int = Color.GRAY
) = requireContext().attrChildColor(id, childId, defValue)

fun Fragment.attrChildColorStateList(@AttrRes id: Int, @AttrRes childId: Int) =
    requireContext().attrChildColorStateList(id, childId)

fun Fragment.attrChildInteger(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildInteger(id, childId, defValue)

fun Fragment.attrChildDimension(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    requireContext().attrChildDimension(id, childId, defValue)

fun Fragment.attrChildDimensionPixelOffset(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildDimensionPixelOffset(id, childId, defValue)

fun Fragment.attrChildDimensionPixelSize(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildDimensionPixelSize(id, childId, defValue)

fun Fragment.attrChildResourcesId(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildResourcesId(id, childId, defValue)

fun Fragment.attrChildDrawable(@AttrRes id: Int, @AttrRes childId: Int) =
    requireContext().attrChildDrawable(id, childId)


fun View.attrChildText(@AttrRes id: Int, @AttrRes childId: Int) = context.attrChildText(id, childId)

fun View.attrChildString(@AttrRes id: Int, @AttrRes childId: Int) =
    context.attrChildString(id, childId)

fun View.attrChildString(@AttrRes id: Int, @AttrRes childId: Int, vararg format: Any?) =
    context.attrChildString(id, childId, *format)

fun View.attrChildBoolean(@AttrRes id: Int, @AttrRes childId: Int, defValue: Boolean) =
    context.attrChildBoolean(id, childId, defValue)

fun View.attrChildInt(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildInt(id, childId, defValue)

fun View.attrChildFloat(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    context.attrChildFloat(id, childId, defValue)

fun View.attrChildColor(
    @AttrRes id: Int, @AttrRes childId: Int, @ColorInt defValue: Int = Color.GRAY
) = context.attrChildColor(id, childId, defValue)

fun View.attrChildColorStateList(@AttrRes id: Int, @AttrRes childId: Int) =
    context.attrChildColorStateList(id, childId)

fun View.attrChildInteger(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildInteger(id, childId, defValue)

fun View.attrChildDimension(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    context.attrChildDimension(id, childId, defValue)

fun View.attrChildDimensionPixelOffset(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildDimensionPixelOffset(id, childId, defValue)

fun View.attrChildDimensionPixelSize(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildDimensionPixelSize(id, childId, defValue)

fun View.attrChildResourcesId(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildResourcesId(id, childId, defValue)

fun View.attrChildDrawable(@AttrRes id: Int, @AttrRes childId: Int) =
    context.attrChildDrawable(id, childId)