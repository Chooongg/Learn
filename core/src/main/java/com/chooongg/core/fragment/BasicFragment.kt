package com.chooongg.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.chooongg.basic.ext.logDClass
import com.chooongg.core.activity.BasicActivity
import com.chooongg.core.ext.getAnnotationTitle

abstract class BasicFragment : Fragment() {

    val fragment get() = this
    val basicActivity get() = activity as? BasicActivity
    fun requireBasicActivity() = requireActivity() as BasicActivity

    var isLoaded = false; private set
    val isShowing get() = !isHidden && isResumed

    open var title: CharSequence? = null; get() = field ?: javaClass.getAnnotationTitle(context)

    open fun getLiftOnScrollTargetId(): Int = ResourcesCompat.ID_NULL

    @LayoutRes
    protected abstract fun initLayout(): Int

    protected open fun initView(savedInstanceState: Bundle?) = Unit

    protected open fun initContent(savedInstanceState: Bundle?) = Unit

    protected open fun initContentByLazy() = Unit

    open fun onReselected() = Unit

    open fun onRefresh(any: Any? = null) = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = if (initLayout() == ResourcesCompat.ID_NULL) null else {
        inflater.inflate(initLayout(), container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logDClass(javaClass, buildString {
            if (title != null) append('(').append(title).append(") ")
            append("onViewCreated")
        })
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initContent(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            isLoaded = true
            initContentByLazy()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isLoaded && !isHidden) {
            isLoaded = true
            initContentByLazy()
        }
    }

    /**
     * @return 是否拦截返回事件
     */
    open fun onBackPressedIntercept() = false

    override fun onDestroyView() {
        super.onDestroyView()
        logDClass(javaClass, buildString {
            if (title != null) append('(').append(title).append(") ")
            append("onDestroyView")
        })
        isLoaded = false
    }
}