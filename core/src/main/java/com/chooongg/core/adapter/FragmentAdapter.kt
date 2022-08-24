package com.chooongg.core.adapter

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chooongg.core.fragment.BasicFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun ViewPager2.bindAppBarLayout(appBarLayout: AppBarLayout) {
    val liftOnScrollSwitchTargetIdCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            (adapter as? FragmentAdapter<out BasicFragment>)
                ?.getLiftOnScrollTargetId(position)
                ?.let { appBarLayout.liftOnScrollTargetViewId = it }
        }
    }
    if (isAttachedToWindow) {
        registerOnPageChangeCallback(liftOnScrollSwitchTargetIdCallback)
    }
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) {
            unregisterOnPageChangeCallback(liftOnScrollSwitchTargetIdCallback)
            registerOnPageChangeCallback(liftOnScrollSwitchTargetIdCallback)
        }

        override fun onViewDetachedFromWindow(view: View) {
            unregisterOnPageChangeCallback(liftOnScrollSwitchTargetIdCallback)
        }
    })
}

fun TabLayout.setupWithViewPager(
    viewPager: ViewPager2,
    autoRefresh: Boolean = true,
    tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy? = null
) {
    TabLayoutMediator(this,
        viewPager,
        autoRefresh,
        tabConfigurationStrategy ?: TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            // TODO: tab.text = "Tab $position"
        })
}

class FragmentAdapter<T : BasicFragment> : FragmentStateAdapter {

    val fragments: MutableList<T>

    constructor(fragmentActivity: FragmentActivity, fragments: MutableList<T>) : super(
        fragmentActivity
    ) {
        this.fragments = fragments
    }

    constructor(fragment: Fragment, fragments: MutableList<T>) : super(fragment) {
        this.fragments = fragments
    }

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragments: MutableList<T>
    ) : super(fragmentManager, lifecycle) {
        this.fragments = fragments
    }

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

    fun getTitle(position: Int) = fragments[position].title

    fun getLiftOnScrollTargetId(position: Int) = fragments[position].getLiftOnScrollTargetId()
}