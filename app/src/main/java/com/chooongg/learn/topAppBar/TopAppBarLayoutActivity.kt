package com.chooongg.learn.topAppBar

import android.os.Bundle
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.isNightMode
import com.chooongg.core.activity.BasicActivity
import com.chooongg.core.widget.NestedScrollWebView
import com.chooongg.learn.R

class TopAppBarLayoutActivity : BasicActivity() {

    override fun initLayout() = when (intent.getStringExtra("type")) {
        "medium" -> R.layout.activity_top_app_bar_layout_medium
        "large" -> R.layout.activity_top_app_bar_layout_large
        else -> R.layout.activity_top_app_bar_layout_small
    }

    override fun initView(savedInstanceState: Bundle?) {
        title = when (intent.getStringExtra("type")) {
            "medium" -> "TopAppBarLayout-Medium"
            "large" -> "TopAppBarLayout-Large"
            else -> "TopAppBarLayout-Small"
        }
        findViewById<NestedScrollWebView>(R.id.web_view).apply {
            settings.javaScriptEnabled = true
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(
                    settings,
                    if (isNightMode()) WebSettingsCompat.FORCE_DARK_ON else WebSettingsCompat.FORCE_DARK_OFF
                )
                this.setBackgroundColor(attrColor(com.google.android.material.R.attr.colorSurface))
            }
            loadUrl("https://www.baidu.com")
        }
    }
}