package com.chooongg.stateLayout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.chooongg.basic.ext.doOnClick
import com.chooongg.stateLayout.animation.StateLayoutAnimation
import com.chooongg.stateLayout.state.AbstractState
import com.chooongg.stateLayout.state.SuccessState
import kotlin.reflect.KClass

class StateLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    private var atFirstIsSuccess: Boolean = false
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    // 是否启用动画
    var enableAnimation: Boolean = false

    // 动画实现类
    var animation: StateLayoutAnimation = StateLayoutManager.animation

    // 当前状态
    var currentState: KClass<out AbstractState> = SuccessState::class

    // 存在的非Success状态
    private val existingOtherState = HashMap<KClass<out AbstractState>, AbstractState>()

    // 重试操作监听
    private var onRetryEventListener: OnRetryEventListener? = null

    // 状态变化监听
    private var onStateChangeListener: OnStateChangeListener? = null

    private var successView = FrameLayout(context).also {
        addView(it)
    }

    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.StateLayout, defStyleAttr, defStyleRes
        )
        atFirstIsSuccess = a.getBoolean(R.styleable.StateLayout_atFirstIsSuccess, atFirstIsSuccess)
        a.recycle()
        if (!isInEditMode && !atFirstIsSuccess && StateLayoutManager.defaultState != SuccessState::class) {
            showState(StateLayoutManager.defaultState)
        }
        enableAnimation = StateLayoutManager.enableAnimation
    }

    fun showState(state: KClass<out AbstractState>, message: CharSequence? = null) {
        if (state == currentState) return
        if (state == SuccessState::class) {
            hideAllOtherState()
            if (canUseAnimation()) animation.createAnimation(successView)
            successView.visibility = View.VISIBLE
            if (canUseAnimation()) animation.showAnimation(successView)
        } else {
            hideAllOtherState()
            val preState = createAlsoShowState(state, message)
            if (preState.isShowSuccess()) {
                successView.visibility = View.VISIBLE
                if (canUseAnimation()) animation.showAnimation(successView)
            } else {
                if (canUseAnimation()) animation.hideAnimation(successView) {
                    successView.visibility = View.GONE
                }
                else successView.visibility = View.GONE
            }
        }
        currentState = state
        onStateChangeListener?.onStateChange(state)
    }

    private fun createAlsoShowState(
        state: KClass<out AbstractState>,
        message: CharSequence? = null
    ): AbstractState {
        val preState = existingOtherState[state] ?: state.java.newInstance().also {
            it.obtainTargetView(context)
            if (canUseAnimation()) animation.createAnimation(it.targetView)
            it.getReloadEventView(it.targetView)?.doOnClick { onRetryEventListener?.onRetry() }
        }
        preState.onAttach(preState.targetView, message)
        if (preState.targetView.parent == null) {
            addView(preState.targetView, LayoutParams(-2, -2, Gravity.CENTER))
        }
        currentState = state
        existingOtherState[state] = preState
        if (canUseAnimation()) animation.showAnimation(preState.targetView)
        return preState
    }

    private fun hideAllOtherState() {
        existingOtherState.forEach { hideState(it.key) }
    }

    private fun hideState(stateClazz: KClass<out AbstractState>) {
        val state = existingOtherState[stateClazz] ?: return
        state.onDetach(state.targetView)
        if (canUseAnimation()) animation.hideAnimation(state.targetView) {
            removeView(state.targetView)
        } else removeView(state.targetView)
        existingOtherState.remove(stateClazz)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        if (child.tag != STATE_ITEM_TAG) {
            successView.addView(child, params)
            return
        }
        super.addView(child, index, params)
    }

    override fun removeView(view: View?) {
        if (view == successView) return
        successView.removeView(view)
        super.removeView(view)
    }

    override fun removeViewInLayout(view: View?) {
        if (view == successView) return
        successView.removeViewInLayout(view)
        super.removeViewInLayout(view)
    }

    override fun removeAllViews() {
        super.removeAllViews()
        successView.removeAllViews()
        addView(successView)
    }

    override fun removeAllViewsInLayout() {
        super.removeAllViewsInLayout()
        successView.removeAllViewsInLayout()
        addViewInLayout(successView, -1, null)
    }

    private fun canUseAnimation() = isAttachedToWindow && enableAnimation

    companion object {
        internal const val STATE_ITEM_TAG = "state_layout_internal_item"
    }
}