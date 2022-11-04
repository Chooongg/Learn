package com.chooongg.form.style

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.attrResourcesId
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.enum.FormBoundaryType
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel

class CardFormStyle : FormStyle(100) {

    private val shapeAppearanceStyleId by lazy {
        context.attrResourcesId(
            com.google.android.material.R.attr.shapeAppearanceCornerMedium, 0
        )
    }

    private val marginSmall by lazy { context.resDimensionPixelSize(R.dimen.formMarginSmall) }
    private val marginMedium by lazy { context.resDimensionPixelSize(R.dimen.formMarginMedium) }

    override fun createItemParentView(parent: ViewGroup) = MaterialCardView(
        parent.context, null, com.google.android.material.R.attr.materialCardViewElevatedStyle
    )

    override fun onBindParentViewHolder(holder: FormViewHolder, boundary: FormBoundary) {
        val cardView = holder.itemView as MaterialCardView
        val shapeAppearance = ShapeAppearanceModel.builder(
            context, shapeAppearanceStyleId, 0
        )
        if (boundary.top == FormBoundaryType.NONE) {
            shapeAppearance.setTopLeftCornerSize(0f).setTopRightCornerSize(0f)
        }
        if (boundary.bottom == FormBoundaryType.NONE) {
            shapeAppearance.setBottomLeftCornerSize(0f).setBottomRightCornerSize(0f)
        }
        cardView.shapeAppearanceModel = shapeAppearance.build()
        cardView.updateLayoutParams<RecyclerView.LayoutParams> {
            topMargin = when (boundary.top) {
                FormBoundaryType.NONE -> 0
                FormBoundaryType.LOCAL -> marginSmall
                FormBoundaryType.GLOBAL -> marginMedium
            }
            bottomMargin = when (boundary.bottom) {
                FormBoundaryType.NONE -> 0
                FormBoundaryType.LOCAL -> marginSmall
                FormBoundaryType.GLOBAL -> marginMedium
            }
        }
    }
}