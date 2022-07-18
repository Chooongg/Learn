package com.chooongg.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.chooongg.basic.ext.logDTag
import com.chooongg.core.activity.BasicActivity
import com.chooongg.core.ext.getAnnotationTitle

abstract class BasicFragment : Fragment() {

    val fragment get() = this
    val basicActivity get() = activity as? BasicActivity
    fun requireBasicActivity() = requireActivity() as BasicActivity

    var title: CharSequence? = null
        get() = field ?: javaClass.getAnnotationTitle(context)

    open fun getLiftOnScrollTargetId(): Int = ResourcesCompat.ID_NULL

    override fun onCreate(savedInstanceState: Bundle?) {
        logDTag("FRAGMENT", "${javaClass.simpleName}(${title}) onCreate")
        super.onCreate(savedInstanceState)
    }
}