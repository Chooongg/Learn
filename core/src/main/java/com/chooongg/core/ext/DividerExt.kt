package com.chooongg.core.ext

import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.DividerBuilder
import com.fondesa.recyclerviewdivider.StaggeredDividerBuilder
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.fondesa.recyclerviewdivider.staggeredDividerBuilder

fun RecyclerView.divider(block: DividerBuilder.() -> Unit) {
    val dividerBuilder = context.dividerBuilder()
    block(dividerBuilder)
    dividerBuilder.build().addTo(this)
}

fun RecyclerView.dividerStaggered(block: StaggeredDividerBuilder.() -> Unit) {
    val dividerBuilder = context.staggeredDividerBuilder()
    block(dividerBuilder)
    dividerBuilder.build().addTo(this)
}