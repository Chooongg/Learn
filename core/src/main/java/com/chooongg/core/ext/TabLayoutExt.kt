package com.chooongg.core.ext

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.google.android.material.tabs.TabLayout

/**
 * 绑定tabLayout文字设置
 */
inline fun <reified T : BaseText> TabLayout.buildText():T {
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
                            textSize = if (selectedTabPosition==i) selectTextSize else normalTextSize
                            if (selectedTabPosition==i)
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