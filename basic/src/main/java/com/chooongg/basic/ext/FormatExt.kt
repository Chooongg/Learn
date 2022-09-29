package com.chooongg.basic.ext

import java.text.DecimalFormat

/**
 * 格式化数字
 *
 * 0 阿拉伯数字
 * # 阿拉伯数字如果不存在就显示为空
 * . 小数分隔符或货币小数分隔符
 * - 减号
 * , 分组分隔符
 * E 分割科学技术法中的尾数和指数。在前缀和后缀中无需添加引号
 * ; 分隔正数和负数子模式
 * ' 用于引用特殊的字符，作为前缀或后缀。
 * % 乘以100并显示为百分数
 * ‰(\u2030) 乘以1000并显示为千分数
 * ¤(\u00A4) 本地化货币符号
 */
fun Number.format(pattern: String): String = DecimalFormat(pattern).format(this)

fun String?.formatNumber(pattern: String) = this?.toDoubleOrNull()?.format(pattern)