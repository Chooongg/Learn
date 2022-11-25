package com.chooongg.form.style

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.*
import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle
import com.chooongg.form.enum.FormBoundaryType
import com.chooongg.form.enum.FormGroupTitleMode
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textview.MaterialTextView
import kotlin.math.max

open class CardFormStyle : DefaultFormStyle() {

    private var partHorizontal = -1
    private var partVertical = -1
    private var partVerticalGlobal = -1

    override fun createItemParentView(parent: ViewGroup) = MaterialCardView(
        parent.context, null, com.google.android.material.R.attr.materialCardViewElevatedStyle
    ).apply {
        if (partHorizontal == -1) partHorizontal = resDimensionPixelSize(R.dimen.formPartHorizontal)
        if (partVertical == -1) partVertical = resDimensionPixelSize(R.dimen.formPartVertical)
        if (partVerticalGlobal == -1) partVerticalGlobal =
            resDimensionPixelSize(R.dimen.formPartVerticalGlobal)
        preventCornerOverlap = false
        rippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
        layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindParentViewHolder(
        manager: FormManager, holder: FormViewHolder, item: BaseForm
    ) {
        with(holder.itemView as MaterialCardView) {
            val shapeAppearance = ShapeAppearanceModel.builder(
                holder.itemView.context, attrResourcesId(
                    com.google.android.material.R.attr.shapeAppearanceCornerMedium, 0
                ), 0
            )
            if (item.adapterTopBoundary == FormBoundaryType.NONE) {
                shapeAppearance.setTopLeftCornerSize(0f).setTopRightCornerSize(0f)
            }
            if (item.adapterBottomBoundary == FormBoundaryType.NONE) {
                shapeAppearance.setBottomLeftCornerSize(0f).setBottomRightCornerSize(0f)
            }
            shapeAppearanceModel = shapeAppearance.build()
            updateLayoutParams<RecyclerView.LayoutParams> {
                marginStart = partHorizontal
                marginEnd = partHorizontal
                topMargin = when (item.adapterTopBoundary) {
                    FormBoundaryType.NONE -> 0
                    FormBoundaryType.LOCAL -> partVertical
                    FormBoundaryType.GLOBAL -> partVerticalGlobal
                }
                bottomMargin = when (item.adapterBottomBoundary) {
                    FormBoundaryType.NONE -> 0
                    FormBoundaryType.LOCAL -> partVertical
                    FormBoundaryType.GLOBAL -> partVerticalGlobal
                }
            }
        }
        holder.getViewOrNull<ViewGroup>(R.id.form_item_layout)?.apply {
            setPaddingRelative(
                0,
                if (item.adapterTopBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize,
                0,
                if (item.adapterBottomBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize
            )
        }
    }

    override fun getGroupTitleLayoutId() = R.layout.form_item_group_name_card

    override fun onBindGroupTitleHolder(
        manager: FormManager,
        adapter: FormGroupAdapter?,
        holder: FormViewHolder,
        item: FormGroupTitle
    ) {
        with(holder.getView<ShapeableImageView>(R.id.form_iv_background)) {
            shapeAppearanceModel = (holder.itemView as MaterialCardView).shapeAppearanceModel
        }
        super.onBindGroupTitleHolder(manager, adapter, holder, item)
    }
}