package com.chooongg.core.inputFilter

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import java.util.regex.Pattern

/**
 * 过滤用户输入只能为金额格式
 */
class CashierInputFilter(
    /**
     * 输入的最大金额
     */
    private val maxNumber: Int = Int.MAX_VALUE,
    /**
     * 小数点后的位数
     */
    private val pointerLength: Int = 2
) : InputFilter {

    companion object {
        private const val POINTER = "."
        private const val ZERO = "0"
    }

    var pattern: Pattern = Pattern.compile("([0-9]|\\.)*")

    /**
     * @param source  新输入的字符串
     * @param start   新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart  原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return     输入内容
     */
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence {
        val sourceText = source.toString()
        val destText = dest.toString()
        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return ""
        }
        val matcher = pattern.matcher(source)
        //已经输入小数点的情况下，只能输入数字
        if (destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return ""
            } else {
                if (POINTER.contentEquals(source)) { //只能输入一个小数点
                    return ""
                }
            }
            //验证小数点精度，保证小数点后只能输入两位
            val index = destText.indexOf(POINTER)
            val length = dend - index
            if (length > pointerLength) {
                return dest.subSequence(dstart, dend)
            }
        } else {
            if (destText == ZERO) { //如果第一个数字为0，第二个不为点，就不允许输入
                if (sourceText != ".") { //第二个不为点时候返回空
                    return ""
                }
            }
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点
            if (!matcher.matches()) {
                return ""
            } else {
                if (POINTER.contentEquals(source) && TextUtils.isEmpty(destText)) {
                    return ""
                }
            }
        }

        //验证输入金额的大小
        val sumText = (destText + sourceText).toDouble()
        return if (sumText > maxNumber) {
            dest.subSequence(dstart, dend)
        } else dest.subSequence(dstart, dend).toString() + sourceText
    }
}