package com.chooongg.core.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils.TruncateAt
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import com.chooongg.basic.ext.attrColor
import com.chooongg.basic.ext.getActivity
import com.chooongg.core.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

@SuppressLint("RestrictedApi")
class TopAppBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    val appBarLayout: AppBarLayout by lazy { findViewById(R.id.appbar_layout) }
    val collapsingToolbarLayout: CollapsingToolbarLayout? by lazy { findViewById(R.id.collapsing_toolbar_layout) }
    val topAppBar: TopAppBar by lazy { findViewById(R.id.top_app_bar) }

    init {
        fitsSystemWindows = true
        setBackgroundColor(attrColor(android.R.attr.colorBackground))
        val a = context.obtainStyledAttributes(attrs, R.styleable.TopAppBarLayout, defStyleAttr, 0)
        when (a.getInt(R.styleable.TopAppBarLayout_appBarType, 0)) {
            0 -> inflate(context, R.layout.learn_top_app_bar_small, this)
            1 -> inflate(context, R.layout.learn_top_app_bar_medium, this)
            2 -> inflate(context, R.layout.learn_top_app_bar_large, this)
        }
        if (a.getBoolean(R.styleable.TopAppBarLayout_setActionBar, true)) {
            (context.getActivity() as? AppCompatActivity)?.setSupportActionBar(topAppBar)
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_title)) {
            val title = a.getString(R.styleable.TopAppBarLayout_title)
            if (a.getBoolean(R.styleable.TopAppBarLayout_setActionBar, true)) {
                context.getActivity()?.title = title
            } else topAppBar.title = title
        } else if (isInEditMode) {
            topAppBar.title = "TITLE"
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_titleCentered)) {
            topAppBar.isTitleCentered =
                a.getBoolean(R.styleable.TopAppBarLayout_titleCentered, false)
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_titleTextAppearance)) {
            topAppBar.setTitleTextAppearance(
                context, a.getResourceId(R.styleable.TopAppBarLayout_titleTextAppearance, 0)
            )
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_subtitle)) {
            topAppBar.subtitle = a.getString(R.styleable.TopAppBarLayout_subtitle)
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_subtitleCentered)) {
            topAppBar.isSubtitleCentered =
                a.getBoolean(R.styleable.TopAppBarLayout_subtitleCentered, false)
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_subtitleTextAppearance)) {
            topAppBar.setSubtitleTextAppearance(
                context, a.getResourceId(R.styleable.TopAppBarLayout_subtitleTextAppearance, 0)
            )
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_titleMargin)) {
            val margin = a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_titleMargin, 0)
            topAppBar.setTitleMargin(margin, margin, margin, margin)
        } else {
            val marginStart =
                a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_titleMarginStart, -1)
            val marginEnd =
                a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_titleMarginEnd, -1)
            val marginTop =
                a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_titleMarginTop, -1)
            val marginBottom =
                a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_titleMarginBottom, -1)
            if (marginStart != -1 || marginEnd != -1 || marginTop != -1 || marginBottom != -1) {
                topAppBar.setTitleMargin(
                    if (marginStart >= 0) marginStart else topAppBar.titleMarginStart,
                    if (marginTop >= 0) marginTop else topAppBar.titleMarginTop,
                    if (marginEnd >= 0) marginEnd else topAppBar.titleMarginEnd,
                    if (marginBottom >= 0) marginBottom else topAppBar.titleMarginBottom
                )
            }
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_titleBackground)) {
            appBarLayout.background = a.getDrawable(R.styleable.TopAppBarLayout_titleBackground)
        }
        collapsingToolbarLayout?.let {
            if (a.hasValue(R.styleable.TopAppBarLayout_titleCollapseEnabled)) {
                it.isTitleEnabled =
                    a.getBoolean(R.styleable.TopAppBarLayout_titleCollapseEnabled, true)
            }
            if (a.hasValue(R.styleable.TopAppBarLayout_titleCollapseMode)) {
                it.titleCollapseMode = a.getInt(R.styleable.TopAppBarLayout_titleCollapseMode, 0)
            }
            if (a.hasValue(R.styleable.TopAppBarLayout_maxLines)) {
                it.maxLines = a.getInteger(R.styleable.TopAppBarLayout_maxLines, 1)
            }
            if (a.hasValue(R.styleable.TopAppBarLayout_collapsedTitleGravity)) {
                it.collapsedTitleGravity = a.getInt(
                    R.styleable.TopAppBarLayout_collapsedTitleGravity,
                    GravityCompat.START or Gravity.CENTER_VERTICAL
                )
            }
            if (a.hasValue(R.styleable.TopAppBarLayout_expandedTitleGravity)) {
                it.expandedTitleGravity = a.getInt(
                    R.styleable.TopAppBarLayout_expandedTitleGravity,
                    GravityCompat.START or Gravity.BOTTOM
                )
            }
            if (a.hasValue(R.styleable.TopAppBarLayout_expandedTitleMargin)) {
                val margin =
                    a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_expandedTitleMargin, 0)
                it.setExpandedTitleMargin(margin, margin, margin, margin)
            } else {
                val marginStart = a.getDimensionPixelOffset(
                    R.styleable.TopAppBarLayout_expandedTitleMarginStart, -1
                )
                val marginEnd = a.getDimensionPixelOffset(
                    R.styleable.TopAppBarLayout_expandedTitleMarginEnd, -1
                )
                val marginTop = a.getDimensionPixelOffset(
                    R.styleable.TopAppBarLayout_expandedTitleMarginTop, -1
                )
                val marginBottom = a.getDimensionPixelOffset(
                    R.styleable.TopAppBarLayout_expandedTitleMarginBottom, -1
                )
                if (marginStart != -1 || marginEnd != -1 || marginTop != -1 || marginBottom != -1) {
                    it.setExpandedTitleMargin(
                        if (marginStart >= 0) marginStart else it.expandedTitleMarginStart,
                        if (marginTop >= 0) marginTop else it.expandedTitleMarginTop,
                        if (marginEnd >= 0) marginEnd else it.expandedTitleMarginEnd,
                        if (marginBottom >= 0) marginBottom else it.expandedTitleMarginBottom
                    )
                }
            }
            if (a.hasValue(R.styleable.TopAppBarLayout_expandedTitleTextAppearance)) {
                it.setExpandedTitleTextAppearance(
                    a.getResourceId(R.styleable.TopAppBarLayout_expandedTitleTextAppearance, 0)
                )
            }
            if (a.hasValue(R.styleable.TopAppBarLayout_collapsedTitleTextAppearance)) {
                it.setCollapsedTitleTextAppearance(
                    a.getResourceId(R.styleable.TopAppBarLayout_collapsedTitleTextAppearance, 0)
                )
            }
            if (a.hasValue(R.styleable.TopAppBarLayout_titleTextEllipsize)) {
                it.setTitleEllipsize(
                    convertEllipsizeToTruncateAt(
                        a.getInt(R.styleable.TopAppBarLayout_titleTextEllipsize, -1)
                    )
                )
            }
            if (a.hasValue(R.styleable.TopAppBarLayout_expandedTitleTextColor)) {
                it.setExpandedTitleTextColor(
                    a.getColorStateList(R.styleable.TopAppBarLayout_expandedTitleTextColor)!!
                )
            } else it.setExpandedTitleTextColor(topAppBar.titleTextColor)
            if (a.hasValue(R.styleable.TopAppBarLayout_collapsedTitleTextColor)) {
                it.setCollapsedTitleTextColor(
                    a.getColorStateList(R.styleable.TopAppBarLayout_collapsedTitleTextColor)!!
                )
            } else it.setCollapsedTitleTextColor(topAppBar.titleTextColor)
        }
        a.recycle()
    }

    @SuppressLint("CustomViewStyleable")
    override fun generateLayoutParams(attrs: AttributeSet?): CoordinatorLayout.LayoutParams {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TopAppBarLayout_Layout)
        if (a.hasValue(R.styleable.TopAppBarLayout_Layout_isTopAppBarChild)) {
            val parentParams = super.generateLayoutParams(attrs)
            val params = LayoutParams(parentParams).apply {
                Log.e("SDFSDFSDFSDFSDF", parentParams.gravity.toString())
                gravity = parentParams.gravity
                Log.e("SDFSDFSDFSDFSDF", gravity.toString())
                isTopAppBarChild =
                    a.getBoolean(R.styleable.TopAppBarLayout_Layout_isTopAppBarChild, false)
            }
            a.recycle()
            return params
        }
        a.recycle()
        return super.generateLayoutParams(attrs)
    }

    private var isHavTopAppBarContent = false

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (params is LayoutParams && params.isTopAppBarChild) {
            val contentParams = Toolbar.LayoutParams(params)
            contentParams.gravity = params.gravity
            topAppBar.addView(child, contentParams)
            if (!isHavTopAppBarContent) {
                isHavTopAppBarContent = true
                collapsingToolbarLayout?.isTitleEnabled = false
                context.getActivity()?.title = null
                topAppBar.title = null
            }
            return
        }
        if (child !is AppBarLayout && params is CoordinatorLayout.LayoutParams && params.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            params.behavior = AppBarLayout.ScrollingViewBehavior()
        }
        super.addView(child, index, params)
    }

    private fun convertEllipsizeToTruncateAt(ellipsize: Int): TruncateAt {
        return when (ellipsize) {
            0 -> TruncateAt.START
            1 -> TruncateAt.MIDDLE
            2 -> TruncateAt.END
            3 -> TruncateAt.MARQUEE
            else -> TruncateAt.END
        }
    }

    class LayoutParams : CoordinatorLayout.LayoutParams {
        var isTopAppBarChild = false
            internal set

        constructor(width: Int, height: Int) : super(width, height)
        constructor(p: MarginLayoutParams?) : super(p)
        constructor(p: ViewGroup.LayoutParams?) : super(p)
        constructor(p: CoordinatorLayout.LayoutParams) : super(p)

    }
}