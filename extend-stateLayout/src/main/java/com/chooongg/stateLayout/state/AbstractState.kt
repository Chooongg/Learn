package com.chooongg.stateLayout.state

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.chooongg.stateLayout.StateLayout

abstract class AbstractState {

    internal lateinit var targetView: View

    private lateinit var context: Context

    internal fun obtainTargetView(context: Context) {
        this.context = context
        targetView = onBuildView(context)
        targetView.tag = StateLayout.STATE_ITEM_TAG
    }

    /**
     * 生成状态View
     */
    protected abstract fun onBuildView(context: Context): View

    /**
     * 绑定时回调
     */
    abstract fun onAttach(view: View, message: CharSequence?)

    /**
     * 修改文本内容
     */
    abstract fun onChangeMessage(view: View, message: CharSequence?)

    /**
     * 获取重新加载事件View
     */
    open fun getReloadEventView(view: View): View? = targetView

    /**
     * 开启隐藏动画后此方法被调用
     */
    open fun onDetach(view: View) = Unit

    /**
     * 关闭隐藏动画后此方法被调用
     */
    open fun onDetach(view: View, removeBlock: () -> Unit) = Unit

    /**
     * 获取状态View布局参数
     */
    open fun getLayoutParams(): FrameLayout.LayoutParams =
        FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )

    /**
     * 是否同时显示成功状态
     */
    open fun isShowSuccess(): Boolean = false

    /**
     * 是否开启显示动画
     */
    open fun isEnableShowAnimation(): Boolean = true

    /**
     * 是否开启隐藏动画
     */
    open fun isEnableHideAnimation(): Boolean = true
}