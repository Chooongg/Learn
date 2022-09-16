package com.chooongg.basic.ext

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

fun Context.resText(@StringRes id: Int) = resources.getText(id)

fun Context.resFont(@FontRes id: Int) = ResourcesCompat.getFont(this, id)

fun Context.resQuantityText(@PluralsRes id: Int, quantity: Int) =
    resources.getQuantityText(id, quantity)

fun Context.resString(@StringRes id: Int) = resources.getString(id)

fun Context.resString(@StringRes id: Int, vararg format: Any?) = resources.getString(id, *format)

fun Context.resQuantityString(@PluralsRes id: Int, quantity: Int, vararg format: Any?) =
    resources.getQuantityString(id, quantity, *format)

fun Context.resQuantityString(@PluralsRes id: Int, quantity: Int) =
    resources.getQuantityString(id, quantity)

fun Context.resText(@StringRes id: Int, def: CharSequence) = resources.getText(id, def)

fun Context.resTextArray(@ArrayRes id: Int): Array<CharSequence> = resources.getTextArray(id)

fun Context.resStringArray(@ArrayRes id: Int): Array<String> = resources.getStringArray(id)

fun Context.resIntArray(@ArrayRes id: Int) = resources.getIntArray(id)

fun Context.resDimension(@DimenRes id: Int) = resources.getDimension(id)

fun Context.resDimensionPixelOffset(@DimenRes id: Int) = resources.getDimensionPixelOffset(id)

fun Context.resDimensionPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

fun Context.resFraction(@FractionRes id: Int, base: Int, pbase: Int) =
    resources.getFraction(id, base, pbase)

fun Context.resDrawable(@DrawableRes id: Int) = ResourcesCompat.getDrawable(resources, id, theme)

fun Context.resColor(@ColorRes id: Int) = ResourcesCompat.getColor(resources, id, theme)

fun Context.resColorStateList(@ColorRes id: Int) =
    ResourcesCompat.getColorStateList(resources, id, theme)

fun Context.resBoolean(@BoolRes id: Int) = resources.getBoolean(id)

fun Context.resInteger(@IntegerRes id: Int) = resources.getInteger(id)

fun Context.resFloat(@DimenRes id: Int) = ResourcesCompat.getFloat(resources, id)

fun Context.resOpenRaw(@RawRes id: Int) = resources.openRawResource(id)

fun Context.resAnimation(@AnimRes id: Int): Animation = AnimationUtils.loadAnimation(this, id)

fun Context.resValue(@AnyRes id: Int, outValue: TypedValue, resolveRefs: Boolean) =
    resources.getValue(id, outValue, resolveRefs)


fun Fragment.resText(@StringRes id: Int) = resources.getText(id)

fun Fragment.resFont(@FontRes id: Int) = ResourcesCompat.getFont(requireContext(), id)

fun Fragment.resQuantityText(@PluralsRes id: Int, quantity: Int) =
    resources.getQuantityText(id, quantity)

fun Fragment.resString(@StringRes id: Int) = resources.getString(id)

fun Fragment.resString(@StringRes id: Int, vararg format: Any?) = resources.getString(id, *format)

fun Fragment.resQuantityString(@PluralsRes id: Int, quantity: Int, vararg format: Any?) =
    resources.getQuantityString(id, quantity, *format)

fun Fragment.resQuantityString(@PluralsRes id: Int, quantity: Int) =
    resources.getQuantityString(id, quantity)

fun Fragment.resText(@StringRes id: Int, def: CharSequence) = resources.getText(id, def)

fun Fragment.resTextArray(@ArrayRes id: Int): Array<CharSequence> = resources.getTextArray(id)

fun Fragment.resStringArray(@ArrayRes id: Int): Array<String> = resources.getStringArray(id)

fun Fragment.resIntArray(@ArrayRes id: Int) = resources.getIntArray(id)

fun Fragment.resDimension(@DimenRes id: Int) = resources.getDimension(id)

fun Fragment.resDimensionPixelOffset(@DimenRes id: Int) = resources.getDimensionPixelOffset(id)

fun Fragment.resDimensionPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

fun Fragment.resFraction(@FractionRes id: Int, base: Int, pbase: Int) =
    resources.getFraction(id, base, pbase)

fun Fragment.resDrawable(@DrawableRes id: Int) =
    ResourcesCompat.getDrawable(resources, id, requireContext().theme)

fun Fragment.resColor(@ColorRes id: Int) =
    ResourcesCompat.getColor(resources, id, requireContext().theme)

fun Fragment.resColorStateList(@ColorRes id: Int) =
    ResourcesCompat.getColorStateList(resources, id, requireContext().theme)

fun Fragment.resBoolean(@BoolRes id: Int) = resources.getBoolean(id)

fun Fragment.resInteger(@IntegerRes id: Int) = resources.getInteger(id)

fun Fragment.resFloat(@DimenRes id: Int) = ResourcesCompat.getFloat(resources, id)

fun Fragment.resOpenRaw(@RawRes id: Int) = resources.openRawResource(id)

fun Fragment.resAnimation(@AnimRes id: Int): Animation =
    AnimationUtils.loadAnimation(requireContext(), id)

fun Fragment.resValue(@AnyRes id: Int, outValue: TypedValue, resolveRefs: Boolean) =
    resources.getValue(id, outValue, resolveRefs)


fun View.resText(@StringRes id: Int) = resources.getText(id)

fun View.resFont(@FontRes id: Int) = ResourcesCompat.getFont(context, id)

fun View.resQuantityText(@PluralsRes id: Int, quantity: Int) =
    resources.getQuantityText(id, quantity)

fun View.resString(@StringRes id: Int) = resources.getString(id)

fun View.resString(@StringRes id: Int, vararg format: Any?) = resources.getString(id, *format)

fun View.resQuantityString(@PluralsRes id: Int, quantity: Int, vararg format: Any?) =
    resources.getQuantityString(id, quantity, *format)

fun View.resQuantityString(@PluralsRes id: Int, quantity: Int) =
    resources.getQuantityString(id, quantity)

fun View.resText(@StringRes id: Int, def: CharSequence) = resources.getText(id, def)

fun View.resTextArray(@ArrayRes id: Int): Array<CharSequence> = resources.getTextArray(id)

fun View.resStringArray(@ArrayRes id: Int): Array<String> = resources.getStringArray(id)

fun View.resIntArray(@ArrayRes id: Int) = resources.getIntArray(id)

fun View.resDimension(@DimenRes id: Int) = resources.getDimension(id)

fun View.resDimensionPixelOffset(@DimenRes id: Int) = resources.getDimensionPixelOffset(id)

fun View.resDimensionPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

fun View.resFraction(@FractionRes id: Int, base: Int, pbase: Int) =
    resources.getFraction(id, base, pbase)

fun View.resDrawable(@DrawableRes id: Int) =
    ResourcesCompat.getDrawable(resources, id, context.theme)

fun View.resColor(@ColorRes id: Int) = ResourcesCompat.getColor(resources, id, context.theme)

fun View.resColorStateList(@ColorRes id: Int) =
    ResourcesCompat.getColorStateList(resources, id, context.theme)

fun View.resBoolean(@BoolRes id: Int) = resources.getBoolean(id)

fun View.resInteger(@IntegerRes id: Int) = resources.getInteger(id)

fun View.resFloat(@DimenRes id: Int) = ResourcesCompat.getFloat(resources, id)

fun View.resOpenRaw(@RawRes id: Int) = resources.openRawResource(id)

fun View.resAnimation(@AnimRes id: Int): Animation = AnimationUtils.loadAnimation(context, id)

fun View.resValue(@AnyRes id: Int, outValue: TypedValue, resolveRefs: Boolean) =
    resources.getValue(id, outValue, resolveRefs)