package com.chooongg.form.style

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.attrResourcesId
import com.chooongg.basic.ext.resDimensionPixelSize
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormGroupAdapter
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle
import com.chooongg.form.enum.FormBoundaryType
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel

open class MaterialCardFormStyle : DefaultFormStyle() {

    private var partHorizontal = -1
    private var partVertical = -1
    private var partVerticalEdge = -1

    override fun createItemParentView(parent: ViewGroup) = MaterialCardView(
        parent.context, null, com.google.android.material.R.attr.materialCardViewElevatedStyle
    ).apply {
        rippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
        layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindParentViewHolder(
        manager: BaseFormManager, holder: FormViewHolder, item: BaseForm
    ) {
        with(holder.itemView as MaterialCardView) {
            if (partHorizontal == -1) partHorizontal =
                resDimensionPixelSize(R.dimen.formPartHorizontal)
            if (partVertical == -1) partVertical = resDimensionPixelSize(R.dimen.formPartVertical)
            if (partVerticalEdge == -1) partVerticalEdge =
                resDimensionPixelSize(R.dimen.formPartVerticalEdge)
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
                topMargin = when (item.adapterTopBoundary) {
                    FormBoundaryType.NONE -> 0
                    FormBoundaryType.LOCAL -> partVertical
                    FormBoundaryType.GLOBAL -> partVerticalEdge
                }
                bottomMargin = when (item.adapterBottomBoundary) {
                    FormBoundaryType.NONE -> 0
                    FormBoundaryType.LOCAL -> partVertical
                    FormBoundaryType.GLOBAL -> partVerticalEdge
                }
                val recyclerViewWidth = manager._recyclerView?.get()?.width ?:0
                if (recyclerViewWidth > manager.itemMaxWidth) {
                    marginStart = (recyclerViewWidth - manager.itemMaxWidth) / 2
                    marginEnd = (recyclerViewWidth - manager.itemMaxWidth) / 2
                } else {
                    marginStart = partHorizontal
                    marginEnd = partHorizontal
                }
            }
            if (childCount > 0) {
                if (item is FormGroupTitle) return@with
                getChildAt(0).updatePaddingRelative(
                    top = if (item.adapterTopBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize,
                    bottom = if (item.adapterBottomBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize
                )
            }
        }
    }

    override fun getGroupTitleLayoutId() = R.layout.form_item_group_name_card

    override fun onBindGroupTitleHolder(
        manager: BaseFormManager,
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