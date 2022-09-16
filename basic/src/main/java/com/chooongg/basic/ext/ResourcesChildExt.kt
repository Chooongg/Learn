package com.chooongg.basic.ext

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.annotation.StyleRes
import androidx.core.content.res.use
import androidx.fragment.app.Fragment

fun Context.resChildText(@StyleRes id: Int, @AttrRes childId: Int) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getText(0) }

fun Context.resChildString(@StyleRes id: Int, @AttrRes childId: Int) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getString(0) }

fun Context.resChildString(
    @StyleRes id: Int, @AttrRes childId: Int, vararg format: Any?
) = obtainStyledAttributes(id, intArrayOf(childId)).use { it.getString(0)?.format(*format) }

fun Context.resChildBoolean(@StyleRes id: Int, @AttrRes childId: Int, defValue: Boolean) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getBoolean(0, defValue) }

fun Context.resChildInt(@StyleRes id: Int, @AttrRes childId: Int, defValue: Int) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getInt(0, defValue) }

fun Context.resChildFloat(@StyleRes id: Int, @AttrRes childId: Int, defValue: Float) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getFloat(0, defValue) }

fun Context.resChildColor(
    @StyleRes id: Int, @AttrRes childId: Int, @ColorInt defValue: Int = Color.GRAY
) = obtainStyledAttributes(id, intArrayOf(childId)).use { it.getColor(0, defValue) }

fun Context.resChildColorStateList(@StyleRes id: Int, @AttrRes childId: Int) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getColorStateList(0) }

fun Context.resChildInteger(@StyleRes id: Int, @AttrRes childId: Int, defValue: Int) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getInteger(0, defValue) }

fun Context.resChildDimension(@StyleRes id: Int, @AttrRes childId: Int, defValue: Float) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getDimension(0, defValue) }

fun Context.resChildDimensionPixelOffset(
    @StyleRes id: Int, @AttrRes childId: Int, defValue: Int
) = obtainStyledAttributes(id, intArrayOf(childId)).use { it.getDimensionPixelOffset(0, defValue) }

fun Context.resChildDimensionPixelSize(
    @StyleRes id: Int, @AttrRes childId: Int, defValue: Int
) = obtainStyledAttributes(id, intArrayOf(childId)).use { it.getDimensionPixelSize(0, defValue) }

fun Context.resChildFraction(
    @StyleRes id: Int, @AttrRes childId: Int, base: Int, pbase: Int, defValue: Float
) = obtainStyledAttributes(id, intArrayOf(childId)).use { it.getFraction(0, base, pbase, defValue) }

fun Context.resChildResourcesId(@StyleRes id: Int, @AttrRes childId: Int, defValue: Int) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getResourceId(0, defValue) }

fun Context.resChildDrawable(@StyleRes id: Int, @AttrRes childId: Int) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getDrawable(0) }

@RequiresApi(Build.VERSION_CODES.O)
fun Context.resChildFont(@StyleRes id: Int, @AttrRes childId: Int) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getFont(0) }

fun Context.resChildTextArray(@StyleRes id: Int, @AttrRes childId: Int) =
    obtainStyledAttributes(id, intArrayOf(childId)).use { it.getTextArray(0) }


fun Fragment.resChildText(@StyleRes id: Int, @AttrRes childId: Int) =
    requireContext().resChildText(id, childId)

fun Fragment.resChildString(@StyleRes id: Int, @AttrRes childId: Int) =
    requireContext().resChildString(id, childId)

fun Fragment.resChildString(
    @StyleRes id: Int, @AttrRes childId: Int, vararg format: Any?
) = requireContext().resChildString(id, childId, *format)

fun Fragment.resChildBoolean(@StyleRes id: Int, @AttrRes childId: Int, defValue: Boolean) =
    requireContext().resChildBoolean(id, childId, defValue)

fun Fragment.resChildInt(@StyleRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().resChildInt(id, childId, defValue)

fun Fragment.resChildFloat(@StyleRes id: Int, @AttrRes childId: Int, defValue: Float) =
    requireContext().resChildFloat(id, childId, defValue)

fun Fragment.resChildColor(
    @StyleRes id: Int, @AttrRes childId: Int, @ColorInt defValue: Int = Color.GRAY
) = requireContext().resChildColor(id, childId, defValue)

fun Fragment.resChildColorStateList(@StyleRes id: Int, @AttrRes childId: Int) =
    requireContext().resChildColorStateList(id, childId)

fun Fragment.resChildInteger(@StyleRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().resChildInteger(id, childId, defValue)

fun Fragment.resChildDimension(@StyleRes id: Int, @AttrRes childId: Int, defValue: Float) =
    requireContext().resChildDimension(id, childId, defValue)

fun Fragment.resChildDimensionPixelOffset(
    @StyleRes id: Int, @AttrRes childId: Int, defValue: Int
) = requireContext().resChildDimensionPixelOffset(id, childId, defValue)

fun Fragment.resChildDimensionPixelSize(
    @StyleRes id: Int, @AttrRes childId: Int, defValue: Int
) = requireContext().resChildDimensionPixelSize(id, childId, defValue)

fun Fragment.resChildFraction(
    @StyleRes id: Int, @AttrRes childId: Int, base: Int, pbase: Int, defValue: Float
) = requireContext().resChildFraction(id, childId, base, pbase, defValue)

fun Fragment.resChildResourcesId(@StyleRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().resChildResourcesId(id, childId, defValue)

fun Fragment.resChildDrawable(@StyleRes id: Int, @AttrRes childId: Int) =
    requireContext().resChildDrawable(id, childId)

@RequiresApi(Build.VERSION_CODES.O)
fun Fragment.resChildFont(@StyleRes id: Int, @AttrRes childId: Int) =
    requireContext().resChildFont(id, childId)

fun Fragment.resChildTextArray(@StyleRes id: Int, @AttrRes childId: Int) =
    requireContext().resChildTextArray(id, childId)


fun View.resChildText(@StyleRes id: Int, @AttrRes childId: Int) = context.resChildText(id, childId)

fun View.resChildString(@StyleRes id: Int, @AttrRes childId: Int) =
    context.resChildString(id, childId)

fun View.resChildString(
    @StyleRes id: Int, @AttrRes childId: Int, vararg format: Any?
) = context.resChildString(id, childId, format)

fun View.resChildBoolean(@StyleRes id: Int, @AttrRes childId: Int, defValue: Boolean) =
    context.resChildBoolean(id, childId, defValue)

fun View.resChildInt(@StyleRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.resChildInt(id, childId, defValue)

fun View.resChildFloat(@StyleRes id: Int, @AttrRes childId: Int, defValue: Float) =
    context.resChildFloat(id, childId, defValue)

fun View.resChildColor(
    @StyleRes id: Int, @AttrRes childId: Int, @ColorInt defValue: Int = Color.GRAY
) = context.resChildColor(id, childId, defValue)

fun View.resChildColorStateList(@StyleRes id: Int, @AttrRes childId: Int) =
    context.resChildColorStateList(id, childId)

fun View.resChildInteger(@StyleRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.resChildInteger(id, childId, defValue)

fun View.resChildDimension(@StyleRes id: Int, @AttrRes childId: Int, defValue: Float) =
    context.resChildDimension(id, childId, defValue)

fun View.resChildDimensionPixelOffset(
    @StyleRes id: Int, @AttrRes childId: Int, defValue: Int
) = context.resChildDimensionPixelOffset(id, childId, defValue)

fun View.resChildDimensionPixelSize(
    @StyleRes id: Int, @AttrRes childId: Int, defValue: Int
) = context.resChildDimensionPixelSize(id, childId, defValue)

fun View.resChildFraction(
    @StyleRes id: Int, @AttrRes childId: Int, base: Int, pbase: Int, defValue: Float
) = context.resChildFraction(id, childId, base, pbase, defValue)

fun View.resChildResourcesId(@StyleRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.resChildResourcesId(id, childId, defValue)

fun View.resChildDrawable(@StyleRes id: Int, @AttrRes childId: Int) =
    context.resChildDrawable(id, childId)

@RequiresApi(Build.VERSION_CODES.O)
fun View.resChildFont(@StyleRes id: Int, @AttrRes childId: Int) = context.resChildFont(id, childId)

fun View.resChildTextArray(@StyleRes id: Int, @AttrRes childId: Int) =
    context.resChildTextArray(id, childId)