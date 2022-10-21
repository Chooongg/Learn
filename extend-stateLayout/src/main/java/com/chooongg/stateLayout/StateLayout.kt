package com.chooongg.stateLayout

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.customview.view.AbsSavedState
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
    private var firstIsSuccess: Boolean = false
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val successView = FrameLayout(context).also {
        it.tag = STATE_ITEM_TAG
        addView(it)
    }

    // 是否启用动画
    var enableAnimation: Boolean = false

    // 动画实现类
    var animation: StateLayoutAnimation = StateLayoutManager.animation

    // 当前状态
    var currentState: KClass<out AbstractState> = SuccessState::class

    // 存在的非Success状态
    private val existingOtherState = HashMap<KClass<out AbstractState>, AbstractState>()

    // 重试操作监听
    private var onRetryEventListener: (() -> Unit)? = null

    // 状态变化监听
    private var onStatedChangeListener: ((KClass<out AbstractState>) -> Unit)? = null

    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.StateLayout, defStyleAttr, defStyleRes
        )
        firstIsSuccess = a.getBoolean(R.styleable.StateLayout_firstIsSuccess, firstIsSuccess)
        a.recycle()
        if (!isInEditMode && !firstIsSuccess && StateLayoutManager.defaultState != SuccessState::class) {
            show(StateLayoutManager.defaultState)
        }
        enableAnimation = StateLayoutManager.enableAnimation
    }

    /**
     * 重试操作监听
     */
    fun setOnRetryEventListener(block: (() -> Unit)?) {
        onRetryEventListener = block
    }

    /**
     * 状态变化监听
     */
    fun setOnStatedChangeListener(block: ((currentState: KClass<out AbstractState>) -> Unit)?) {
        onStatedChangeListener = block
    }

    /**
     * 显示成功状态
     */
    fun showSuccess() {
        show(SuccessState::class)
    }

    /**
     * 显示状态
     */
    fun show(state: KClass<out AbstractState>, message: CharSequence? = null) {
        if (state == currentState) return
        post {
            if (state == SuccessState::class) {
                hideAllOtherState()
                if (canUseAnimation() && successView.visibility != View.VISIBLE)
                    animation.createAnimation(successView)
                successView.visibility = View.VISIBLE
                if (canUseAnimation()) animation.showAnimation(successView)
            } else {
                hideAllOtherState()
                val preState = createAlsoShowState(state, message)
                if (preState.isShowSuccess()) {
                    successView.visibility = View.VISIBLE
                    if (canUseAnimation())
                        animation.showAnimation(successView)
                } else {
                    if (canUseAnimation())
                        animation.hideAnimation(successView) {
                            successView.visibility = View.GONE
                        }
                    else successView.visibility = View.GONE
                }
            }
            currentState = state
            onStatedChangeListener?.invoke(state)
        }
    }

    private fun createAlsoShowState(
        state: KClass<out AbstractState>,
        message: CharSequence? = null
    ): AbstractState {
        val preState = existingOtherState[state] ?: state.java.newInstance().also {
            it.obtainTargetView(context)
            if (canUseAnimation() && it.isEnableShowAnimation()) animation.createAnimation(it.targetView)
            it.getReloadEventView(it.targetView)?.doOnClick { onRetryEventListener?.invoke() }
        }
        preState.onAttach(preState.targetView, message)
        if (preState.targetView.parent == null) {
            addView(preState.targetView, preState.getLayoutParams())
        }
        currentState = state
        existingOtherState[state] = preState
        if (canUseAnimation() && preState.isEnableShowAnimation()) animation.showAnimation(preState.targetView)
        return preState
    }

    private fun hideAllOtherState() {
        existingOtherState.forEach { hideState(it.key) }
    }

    private fun hideState(stateClazz: KClass<out AbstractState>) {
        val state = existingOtherState[stateClazz] ?: return
        if (canUseAnimation()) {
            if (state.isEnableHideAnimation()) {
                animation.hideAnimation(state.targetView) {
                    state.onDetach(state.targetView)
                    removeView(state.targetView)
                }
            } else state.onDetach(state.targetView) { removeView(state.targetView) }
        } else {
            state.onDetach(state.targetView)
            removeView(state.targetView)
        }
        existingOtherState.remove(stateClazz)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child != null && child.tag != STATE_ITEM_TAG) {
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

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        if (state.currentState != null) {
            show(state.currentState!!)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val ss = SavedState(super.onSaveInstanceState()!!)
        ss.currentState = currentState
        return ss
    }

    @Suppress("UNCHECKED_CAST")
    private class SavedState : AbsSavedState {
        var currentState: KClass<out AbstractState>? = null

        constructor(superState: Parcelable) : super(superState)
        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader) {
            currentState = source.readString()?.let {
                (Class.forName(it) as Class<AbstractState>).kotlin
            }
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeString(currentState?.toString())
        }

        companion object CREATOR : Parcelable.ClassLoaderCreator<SavedState> {
            override fun createFromParcel(source: Parcel, loader: ClassLoader?): SavedState {
                return SavedState(source, loader)
            }

            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel, null)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

    private fun canUseAnimation() = isAttachedToWindow && enableAnimation

    companion object {
        internal const val STATE_ITEM_TAG = "state_layout_internal_item"
    }
}