package com.chooongg.core.ext

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.MenuInflater
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import com.google.android.material.tabs.TabLayout

/**
 * 绑定tabLayout文字设置
 */
inline fun <reified T : BaseText> TabLayout.buildText(): T {
    val text = T::class.java.newInstance()
    text.bindTabLayout(this)
    return text
}

open class BaseText {
    protected var context: Context? = null
    protected var tabLayout: TabLayout? = null
    protected var normalTextBold: Boolean = false
    protected var selectTextBold: Boolean = false
    protected var normalTextSize: Float = 14f
    protected var selectTextSize: Float = 14f

    fun bindTabLayout(tabLayout: TabLayout) {
        this.tabLayout = tabLayout
        this.context = tabLayout.context
    }

    fun setNormalTextBold(normalTextBold: Boolean): BaseText {
        this.normalTextBold = normalTextBold
        return this
    }

    fun setSelectTextBold(selectTextBold: Boolean): BaseText {
        this.selectTextBold = selectTextBold
        return this
    }

    fun setNormalTextSize(normalTextSize: Float): BaseText {
        this.normalTextSize = normalTextSize
        return this
    }

    fun setSelectTextSize(selectTextSize: Float): BaseText {
        this.selectTextSize = selectTextSize
        return this
    }

    fun bind() {
        tabLayout?.post {
            tabLayout?.apply {
                for (i in 0 until tabCount) {
                    getTabAt(i)?.let {
                        it.customView = TextView(context).apply {
                            text = it.text
                            textSize =
                                if (selectedTabPosition == i) selectTextSize else normalTextSize
                            if (selectedTabPosition == i)
                                paint?.isFakeBoldText = selectTextBold
                            else
                                paint?.isFakeBoldText = normalTextBold
                            gravity = Gravity.CENTER
                            setTextColor(tabTextColors)
                        }
                    }
                }

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        (tab?.customView as? TextView)?.apply {
                            textSize = selectTextSize
                            paint?.isFakeBoldText = selectTextBold
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        (tab?.customView as? TextView)?.apply {
                            textSize = normalTextSize
                            paint?.isFakeBoldText = normalTextBold
                        }
                    }

                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        (tab?.customView as? TextView)?.apply {
                            textSize = selectTextSize
                            paint?.isFakeBoldText = selectTextBold
                        }
                    }
                })
            }
        }
    }
}


@Deprecated("deprecated", ReplaceWith("inflateMenu(resId)"))
fun TabLayout.setMenu(@MenuRes resId: Int) = inflateMenu(resId)

@SuppressLint("RestrictedApi")
fun TabLayout.inflateMenu(@MenuRes resId: Int) {
    removeAllTabs()
    val builder = MenuBuilder(context)
    MenuInflater(context).inflate(resId, builder)
    builder.visibleItems.forEach {
        addTab(newTab().apply {
            id = it.itemId
            text = it.title
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tooltipText = it.tooltipText ?: it.title
            }
            if (it.icon != null) icon = it.icon
        })
    }
}

var TabLayout.selectedTabId: Int?
    get() = getTabAt(selectedTabPosition)?.id
    set(value) {
        tabFor@ for (i in 0 until tabCount) {
            val tab = getTabAt(i) ?: continue@tabFor
            if (tab.id == value) {
                tab.select()
                return
            }
        }
    }
