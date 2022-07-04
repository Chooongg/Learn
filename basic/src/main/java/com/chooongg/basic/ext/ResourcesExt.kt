package com.chooongg.basic.ext

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Context.resString(@StringRes id: Int) =
    resources.getString(id)

fun Context.resString(@StringRes id: Int, vararg format: Any?) =
    resources.getString(id, *format)

fun Context.resText(@StringRes id: Int) =
    resources.getText(id)

fun Context.resTextArray(@ArrayRes id: Int): Array<CharSequence> =
    resources.getTextArray(id)

fun Context.resStringArray(@ArrayRes id: Int): Array<String> =
    resources.getStringArray(id)

fun Context.resIntArray(@ArrayRes id: Int) =
    resources.getIntArray(id)

fun Context.resDimension(@DimenRes id: Int) =
    resources.getDimension(id)

fun Context.resDimensionPixelOffset(@DimenRes id: Int) =
    resources.getDimensionPixelOffset(id)

fun Context.resDimensionPixelSize(@DimenRes id: Int) =
    resources.getDimensionPixelSize(id)

fun Context.resDrawable(@DrawableRes id: Int) =
    ContextCompat.getDrawable(this, id)

fun Context.resColor(@ColorRes id: Int) =
    ContextCompat.getColor(this, id)

fun Context.resColorStateList(@ColorRes id: Int) =
    ContextCompat.getColorStateList(this, id)

fun Context.resBoolean(@BoolRes id: Int) =
    resources.getBoolean(id)

fun Context.resInteger(@IntegerRes id: Int) =
    resources.getInteger(id)

fun Context.resOpenRaw(@RawRes id: Int) =
    resources.openRawResource(id)

fun Context.resAnimation(@AnimRes id: Int): Animation =
    AnimationUtils.loadAnimation(this, id)

fun Fragment.resString(@StringRes id: Int) =
    requireContext().resString(id)

fun Fragment.resString(@StringRes id: Int, vararg format: Any?) =
    requireContext().resString(id, *format)

fun Fragment.resText(@StringRes id: Int) =
    requireContext().resText(id)

fun Fragment.resTextArray(@ArrayRes id: Int) =
    requireContext().resTextArray(id)

fun Fragment.resStringArray(@ArrayRes id: Int) =
    requireContext().resStringArray(id)

fun Fragment.resIntArray(@ArrayRes id: Int) =
    requireContext().resIntArray(id)

fun Fragment.resDimension(@DimenRes id: Int) =
    requireContext().resDimension(id)

fun Fragment.resDimensionPixelOffset(@DimenRes id: Int) =
    requireContext().resDimensionPixelOffset(id)

fun Fragment.resDimensionPixelSize(@DimenRes id: Int) =
    requireContext().resDimensionPixelSize(id)

fun Fragment.resDrawable(@DrawableRes id: Int) =
    requireContext().resDrawable(id)

fun Fragment.resColor(@ColorRes id: Int) =
    requireContext().resColor(id)

fun Fragment.resColorStateList(@ColorRes id: Int) =
    requireContext().resColorStateList(id)

fun Fragment.resBoolean(@BoolRes id: Int) =
    requireContext().resBoolean(id)

fun Fragment.resInteger(@IntegerRes id: Int) =
    requireContext().resInteger(id)

fun Fragment.resOpenRaw(@RawRes id: Int) =
    requireContext().resOpenRaw(id)

fun Fragment.resAnimation(@AnimRes id: Int): Animation =
    requireContext().resAnimation(id)

fun View.resText(@StringRes id: Int) =
    context.resText(id)

fun View.resTextArray(@ArrayRes id: Int) =
    context.resTextArray(id)

fun View.resStringArray(@ArrayRes id: Int) =
    context.resStringArray(id)

fun View.resIntArray(@ArrayRes id: Int) =
    context.resIntArray(id)

fun View.resDimension(@DimenRes id: Int) =
    context.resDimension(id)

fun View.resDimensionPixelOffset(@DimenRes id: Int) =
    context.resDimensionPixelOffset(id)

fun View.resDimensionPixelSize(@DimenRes id: Int) =
    context.resDimensionPixelSize(id)

fun View.resDrawable(@DrawableRes id: Int) =
    context.resDrawable(id)

fun View.resColor(@ColorRes id: Int) =
    context.resColor(id)

fun View.resColorStateList(@ColorRes id: Int) =
    context.resColorStateList(id)

fun View.resBoolean(@BoolRes id: Int) =
    context.resBoolean(id)

fun View.resInteger(@IntegerRes id: Int) =
    context.resInteger(id)

fun View.resOpenRaw(@RawRes id: Int) =
    context.resOpenRaw(id)

fun View.resAnimation(@AnimRes id: Int): Animation =
    context.resAnimation(id)