package com.chooongg.basic.utils

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import com.google.android.material.color.MaterialColors

object ColorUtils {

    /**
     * 设置透明度
     */
    fun setAlpha(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) alpha: Int
    ) = color and 0x00ffffff or (alpha shl 24)

    /**
     * 设置透明度
     */
    fun setAlpha(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float
    ) = color and 0x00ffffff or ((alpha * 255.0f + 0.5f).toInt() shl 24)

    fun setRedComponent(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) red: Int
    ): Int {
        return color and -0xff0001 or (red shl 16)
    }

    fun setRedComponent(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) red: Float
    ): Int {
        return color and -0xff0001 or ((red * 255.0f + 0.5f).toInt() shl 16)
    }

    fun setGreenComponent(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) green: Int
    ): Int {
        return color and -0xff01 or (green shl 8)
    }

    fun setGreenComponent(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) green: Float
    ): Int {
        return color and -0xff01 or ((green * 255.0f + 0.5f).toInt() shl 8)
    }

    fun setBlueComponent(
        @ColorInt color: Int,
        @IntRange(from = 0x0, to = 0xFF) blue: Int
    ): Int {
        return color and -0x100 or blue
    }

    fun setBlueComponent(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) blue: Float
    ): Int {
        return color and -0x100 or (blue * 255.0f + 0.5f).toInt()
    }

    fun int2RgbString(@ColorInt colorInt: Int): String {
        val colorIntTemp = colorInt and 0x00ffffff
        var color = Integer.toHexString(colorIntTemp)
        while (color.length < 6) {
            color = "0$color"
        }
        return "#$color"
    }

    fun int2ArgbString(@ColorInt colorInt: Int): String {
        var color = Integer.toHexString(colorInt)
        while (color.length < 6) {
            color = "0$color"
        }
        while (color.length < 8) {
            color = "f$color"
        }
        return "#$color"
    }

    fun getRandomColor(supportAlpha: Boolean = false): Int {
        val high = if (supportAlpha) (Math.random() * 0x100).toInt() shl 24 else -0x1000000
        return high or (Math.random() * 0x1000000).toInt()
    }

    fun isLightColor(@ColorInt color: Int) = MaterialColors.isColorLight(color)
}