package com.chooongg.core.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils.TruncateAt
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.Insets
import androidx.core.view.*
import com.chooongg.basic.ext.*
import com.chooongg.core.R
import com.chooongg.core.annotation.ActivityEdgeToEdge
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

@SuppressLint("RestrictedApi")
class TopAppBarLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private val edgeToEdge = context::class.java.getAnnotation(ActivityEdgeToEdge::class.java)

    val appBarLayout: AppBarLayout by lazy { findViewById(R.id.appbar_layout) }
    val topAppBar: TopAppBar by lazy { findViewById(R.id.top_app_bar) }
    val collapsingToolbarLayout: CollapsingToolbarLayout? by lazy { findViewById(R.id.collapsing_toolbar_layout) }

    private var expandedTitleMarginStart: Int = 0
    private var expandedTitleMarginTop: Int = 0
    private var expandedTitleMarginEnd: Int = 0
    private var expandedTitleMarginBottom: Int = 0

    init {
        clipToPadding = false
        val a = context.obtainStyledAttributes(attrs, R.styleable.TopAppBarLayout, defStyleAttr, 0)
        when (a.getInt(R.styleable.TopAppBarLayout_appBarType, 0)) {
            0 -> inflate(context, R.layout.learn_top_app_bar_small, this)
            1 -> inflate(context, R.layout.learn_top_app_bar_medium, this)
            2 -> inflate(context, R.layout.learn_top_app_bar_large, this)
        }
        val activity = context.getActivity()
        if (context.attrBoolean(androidx.appcompat.R.attr.windowActionBar, false)) {
            if (collapsingToolbarLayout != null) appBarLayout.removeView(collapsingToolbarLayout)
            else appBarLayout.removeView(topAppBar)
        } else if (a.getBoolean(R.styleable.TopAppBarLayout_setActionBar, true)) {
            (activity as? AppCompatActivity)?.setSupportActionBar(topAppBar)
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_liftOnScroll)) {
            appBarLayout.isLiftOnScroll =
                a.getBoolean(R.styleable.TopAppBarLayout_liftOnScroll, true)
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_liftOnScrollTargetViewId)) {
            appBarLayout.liftOnScrollTargetViewId =
                a.getResourceId(R.styleable.TopAppBarLayout_liftOnScrollTargetViewId, 0)
        }
        if (a.hasValue(R.styleable.TopAppBarLayout_title)) {
            val title = a.getString(R.styleable.TopAppBarLayout_title)
            if (a.getBoolean(R.styleable.TopAppBarLayout_setActionBar, true)) {
                activity?.title = title
            } else topAppBar.title = title
        } else if (isInEditMode) {
            topAppBar.title = resString(android.R.string.dialog_alert_title)
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
        if (a.hasValue(R.styleable.TopAppBarLayout_titleBackground)) {
            appBarLayout.background = a.getDrawable(R.styleable.TopAppBarLayout_titleBackground)
        }
        val defaultMargin = resDimensionPixelSize(com.chooongg.basic.R.dimen.contentMedium)
        expandedTitleMarginStart =
            a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_titleMarginStart, defaultMargin)
        expandedTitleMarginTop =
            a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_titleMarginTop, defaultMargin)
        expandedTitleMarginEnd =
            a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_titleMarginEnd, defaultMargin)
        expandedTitleMarginBottom =
            a.getDimensionPixelOffset(R.styleable.TopAppBarLayout_titleMarginBottom, defaultMargin)
        collapsingToolbarLayout?.let {
            it.setExpandedTitleMargin(
                expandedTitleMarginStart, expandedTitleMarginTop,
                expandedTitleMarginEnd, expandedTitleMarginBottom
            )
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
        if (edgeToEdge?.isEdgeToEdge == true
            && (parent as? ViewGroup)?.id != android.R.id.content
            && tag != resString(R.string.keepWindowInsetsPadding)
        ) {
            ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
                updateWindowInsets(
                    insets.getInsets(
                        WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
                    )
                )
                insets
            }
        }
    }

    fun updateWindowInsets(insets: Insets) {
        if (edgeToEdge?.isEdgeToEdge != true) return
        val left = edgeToEdge.fitsSide and ActivityEdgeToEdge.LEFT != 0
        val top = edgeToEdge.fitsSide and ActivityEdgeToEdge.TOP != 0
        val right = edgeToEdge.fitsSide and ActivityEdgeToEdge.RIGHT != 0
        val bottom = edgeToEdge.fitsSide and ActivityEdgeToEdge.BOTTOM != 0
        setPadding(
            if (left) insets.left else 0,
            if (top) insets.top else 0,
            if (right) insets.right else 0,
            if (bottom) insets.bottom else 0
        )
        appBarLayout.updateLayoutParams<MarginLayoutParams> {
            setMargins(
                if (left) -insets.left else 0,
                if (top) -insets.top else 0,
                if (right) -insets.right else 0,
                0
            )
        }
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout!!.setPadding(
                if (left) insets.left else 0,
                0,
                if (right) insets.right else 0,
                0
            )
            val isRtl = layoutDirection == LAYOUT_DIRECTION_RTL
            collapsingToolbarLayout!!.setExpandedTitleMargin(
                expandedTitleMarginStart + if (isRtl) insets.right else insets.left,
                expandedTitleMarginTop,
                expandedTitleMarginEnd + if (isRtl) insets.left else insets.right,
                expandedTitleMarginBottom
            )
        } else topAppBar.setPadding(
            if (left) insets.left else 0,
            0,
            if (right) insets.right else 0,
            0
        )
    }

    @SuppressLint("CustomViewStyleable")
    override fun generateLayoutParams(attrs: AttributeSet?): CoordinatorLayout.LayoutParams {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TopAppBarLayout_Layout)
        if (a.hasValue(R.styleable.TopAppBarLayout_Layout_isTopAppBarChild)
            || a.hasValue(R.styleable.TopAppBarLayout_Layout_isAppBarLayoutChild)
        ) {
            val parentParams = super.generateLayoutParams(attrs)
            val params = LayoutParams(parentParams).apply {
                isTopAppBarChild = a.getBoolean(
                    R.styleable.TopAppBarLayout_Layout_isTopAppBarChild, false
                )
                isAppBarLayoutChild = a.getBoolean(
                    R.styleable.TopAppBarLayout_Layout_isAppBarLayoutChild, false
                )
                scrollFlags = a.getInt(
                    R.styleable.TopAppBarLayout_Layout_layout_scrollFlags,
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                )
            }
            a.recycle()
            return params
        }
        a.recycle()
        return super.generateLayoutParams(attrs)
    }

    private var isHavTopAppBarContent = false

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (params is LayoutParams) {
            if (params.isTopAppBarChild) {
                val contentParams = Toolbar.LayoutParams(params)
                contentParams.gravity = params.gravity
                topAppBar.addView(child, contentParams)
                if (!isHavTopAppBarContent) {
                    isHavTopAppBarContent = true
                    findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
                        ?.isTitleEnabled = false
                    topAppBar.title = null
                }
                return
            } else if (params.isAppBarLayoutChild) {
                val contentParams = AppBarLayout.LayoutParams(params)
                contentParams.gravity = params.gravity
                contentParams.scrollFlags = params.scrollFlags
                appBarLayout.addView(child, contentParams)
                return
            }
        }
        if (child !is AppBarLayout) {
            if (params is CoordinatorLayout.LayoutParams
                && params.width == ViewGroup.LayoutParams.MATCH_PARENT
                && params.height == ViewGroup.LayoutParams.MATCH_PARENT
            ) {
                params.behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
        if (child is BottomNavigationView) {
            ViewCompat.setOnApplyWindowInsetsListener(child) { view, insets ->
                child.updateLayoutParams<MarginLayoutParams> {
                    setMargins(-paddingLeft, 0, -paddingRight, -paddingBottom)
                }
                val isRtl = layoutDirection == LAYOUT_DIRECTION_RTL
                child.setPaddingRelative(
                    if (isRtl) paddingRight else paddingLeft,
                    0,
                    if (isRtl) paddingLeft else paddingRight,
                    paddingBottom
                )
                insets
            }
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

    class LayoutParams(p: CoordinatorLayout.LayoutParams) : CoordinatorLayout.LayoutParams(p) {
        var isTopAppBarChild = false
            internal set
        var isAppBarLayoutChild = false
            internal set

        @AppBarLayout.LayoutParams.ScrollFlags
        var scrollFlags: Int = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL

        init {
            gravity = p.gravity
        }
    }
}