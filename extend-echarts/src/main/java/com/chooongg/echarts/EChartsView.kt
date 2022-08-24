package com.chooongg.echarts

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.annotation.Keep
import androidx.core.content.getSystemService
import org.json.JSONObject

@Suppress("DEPRECATION")
@SuppressLint("SetJavaScriptEnabled")
class EChartsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val webView = WebView(context.applicationContext)

    private var debug: Boolean = isAppDebug()

    private val shouldCallJsFunctionArray = ArrayList<String>()

    private var isFinished = false

    init {
        webView.overScrollMode = OVER_SCROLL_NEVER
        webView.settings.let {
            it.javaScriptEnabled = true
            it.javaScriptCanOpenWindowsAutomatically = true
            it.displayZoomControls = false
            it.setSupportZoom(false)
            this.setBackgroundColor(0)
        }
        webView.addJavascriptInterface(EChartInterface(context), "Android")
        initWebViewClient()
        addView(webView)
        if (isNightMode()) {
            webView.loadUrl("file:///android_asset/echartsNight.html")
        } else {
            webView.loadUrl("file:///android_asset/echartsDay.html")
        }
    }

    fun setDebug(debug: Boolean) {
        this.debug = debug
    }

    fun setOption(json: JSONObject) {
        loadJavaScript("javascript:setOption('${json}')")
    }

    private fun loadJavaScript(function: String) {
        if (!isFinished) {
            shouldCallJsFunctionArray.add(function)
            return
        }
        webView.evaluateJavascript(function, null)
    }

    private fun initWebViewClient() {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                isFinished = true
                shouldCallJsFunctionArray.forEach { webView.evaluateJavascript(it, null) }
                shouldCallJsFunctionArray.clear()
            }
        }
    }

    private fun isNightMode(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    @Keep
    inner class EChartInterface(val context: Context) {

        @JavascriptInterface
        fun showDebugMessage(message: String) {
            if (debug) Log.v("EChartsView", message)
        }

        /**添加图表事件响应监听
         * @param params echart返回的事件信息 http://echarts.baidu.com/api.html#events
         */
        @JavascriptInterface
        fun addEChartActionHandlerResponseResult(params: String?) {
            if (debug && !params.isNullOrEmpty()) Log.d("EChartsView", params)
            onAddEChartActionHandlerResponseResultListener?.actionHandlerResponseResult(params)
        }


        /**移除图表事件响应监听
         * @param params echart返回的事件信息 http://echarts.baidu.com/api.html#events
         */
        @JavascriptInterface
        fun removeEChartActionHandlerResponseResult(params: String?) {
            if (debug && !params.isNullOrEmpty()) Log.d("EChartsView", params)
        }
    }

    ////////////////////////////添加事件监听eChart返回的 事件相关属性（是一个json），将该json返回给开发者使用
    ///////////////////////////eChart返回的事件信息:http://echarts.baidu.com/api.html#events

    ////////////////////////////添加事件监听eChart返回的 事件相关属性（是一个json），将该json返回给开发者使用
    ///////////////////////////eChart返回的事件信息:http://echarts.baidu.com/api.html#events
    private var onAddEChartActionHandlerResponseResultListener: OnAddEChartActionHandlerResponseResultListener? =
        null

    fun setOnAddEChartActionHandlerResponseResultListener(
        onAddEChartActionHandlerResponseResultListener: OnAddEChartActionHandlerResponseResultListener?
    ) {
        this.onAddEChartActionHandlerResponseResultListener =
            onAddEChartActionHandlerResponseResultListener
    }

    fun getOnAddEChartActionHandlerResponseResultListener(): OnAddEChartActionHandlerResponseResultListener? {
        return onAddEChartActionHandlerResponseResultListener
    }

    interface OnAddEChartActionHandlerResponseResultListener {
        fun actionHandlerResponseResult(result: String?)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        webView.removeAllViews()
        webView.destroy()
    }

    private fun isAppDebug(): Boolean {
        if (context.packageName.isBlank()) return false
        return try {
            val ai = context.getSystemService<PackageManager>()!!
                .getApplicationInfo(context.packageName, 0)
            ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }
}