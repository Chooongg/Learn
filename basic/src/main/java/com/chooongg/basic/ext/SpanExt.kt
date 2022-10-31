package com.chooongg.basic.ext

import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.widget.TextView
import com.chooongg.basic.LearnFrameException
import com.chooongg.basic.utils.SpanUtils

fun CharSequence.style(init: SpanUtils.() -> Unit): SpanUtils = SpanUtils(this).apply {
    init()
    return this
}

fun TextView.setText(span: SpanUtils) {
    for (style in span.styles.iterator()) {
        if (style is URLSpan) {
            movementMethod = LinkMovementMethod()
            break
        }
    }
    text = span.toSpannableString()
}

var TextView.textSpan: SpanUtils
    set(value) = setText(value)
    get() {
        throw LearnFrameException("Span Text not get value")
    }