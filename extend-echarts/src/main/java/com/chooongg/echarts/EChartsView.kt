package com.chooongg.echarts

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.ArrayMap
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.Keep
import org.json.JSONObject

@Suppress("DEPRECATION")
@SuppressLint("SetJavaScriptEnabled")
class EChartsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    private var debug: Boolean = true

    private val shouldCallJsFunctionArray = ArrayList<String>()

    private var isFinished = false

    init {
        overScrollMode = OVER_SCROLL_NEVER
        settings.let {
            it.javaScriptCanOpenWindowsAutomatically = true
            it.displayZoomControls = false
            it.setSupportZoom(false)
            this.setBackgroundColor(0)
        }
        addJavascriptInterface(EChartInterface(context), "Android")
        initWebViewClient()
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
        evaluateJavascript(function, null)
    }

    private fun initWebViewClient() {
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                isFinished = true
                shouldCallJsFunctionArray.forEach { evaluateJavascript(it, null) }
                shouldCallJsFunctionArray.clear()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        settings.javaScriptEnabled = true
        if (isNightMode()) {
            loadUrl("file:///android_asset/echartsNight.html")
        } else {
            loadUrl("file:///android_asset/echartsDay.html")
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
        isFinished = false
        settings.javaScriptEnabled = false
        removeAllViews()
        destroy()
        super.onDetachedFromWindow()
    }
}