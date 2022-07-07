package com.chooongg.stateLayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
    var enableAnimation: Boolean = StateLayoutManager.enableAnimation

    // 动画实现类
    var animation: KClass<out StateLayoutAnimation> = StateLayoutManager.animation

    // 当前状态
    var currentState: KClass<out AbstractState> = SuccessState::class

    // 存在的非Success状态
    private val existingState = HashMap<KClass<out AbstractState>, AbstractState>()

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
        if (!isInEditMode && !atFirstIsSuccess) {

        }
        a.recycle()
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

    companion object {
        internal const val STATE_ITEM_TAG = "state_layout_internal_item"
    }
}