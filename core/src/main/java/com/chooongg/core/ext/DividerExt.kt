package com.chooongg.core.ext

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.DividerBuilder
import com.fondesa.recyclerviewdivider.StaggeredDividerBuilder
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.fondesa.recyclerviewdivider.staggeredDividerBuilder

fun RecyclerView.divider(block: DividerBuilder.() -> Unit) {
    val dividerBuilder = context.dividerBuilder()
    block(dividerBuilder)
    val divider = dividerBuilder.build()
    divider.addTo(this)
}

fun RecyclerView.dividerFitsNavigationBar() {
    var itemDecoration: RecyclerView.ItemDecoration? = null
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val navigationInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
        if (itemDecoration != null) removeItemDecoration(itemDecoration!!)
        val divider = context.dividerBuilder().apply {
            asSpace().size(navigationInsets.bottom)
            visibilityProvider { _, divider -> divider.isLastDivider }
        }.build()
        itemDecoration = divider
        divider.addTo(this)
        insets
    }
}

fun RecyclerView.dividerStaggered(block: StaggeredDividerBuilder.() -> Unit) {
    val dividerBuilder = context.staggeredDividerBuilder()
    block(dividerBuilder)
    dividerBuilder.build().addTo(this)
}

fun DividerBuilder.showAllDivider() = showFirstDivider().showLastDivider().showSideDividers()