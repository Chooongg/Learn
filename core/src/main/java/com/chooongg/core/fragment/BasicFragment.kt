package com.chooongg.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        logDTag("FRAGMENT", "${javaClass.simpleName}(${title}) onCreate")
        super.onCreate(savedInstanceState)
    }
}