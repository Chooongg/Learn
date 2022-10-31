package com.chooongg.form

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*

class FormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.recyclerview.R.attr.recyclerViewStyle
) : RecyclerView(context, attrs, defStyleAttr) {

    val adapter = ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build())

    init {
        if (layoutManager == null) {
            FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.NOWRAP).apply {
                alignItems = AlignItems.CENTER
                justifyContent = JustifyContent.CENTER
            }.also { layoutManager = it }
        }
        setAdapter(adapter)

    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
    }

}