package com.chooongg.basic.ext

import android.os.Build
import android.text.Html

@Suppress("DEPRECATION")
fun String.fromHtml() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
    this,
    Html.FROM_HTML_MODE_LEGACY
) else Html.fromHtml(this)