package com.chooongg.form.style

import android.view.LayoutInflater
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

open class Material3CardFormStyle : DefaultFormStyle() {

    private var partHorizontal = -1
    private var partVertical = -1
    private var partVerticalEdge = -1

    override fun createItemParentView(parent: ViewGroup) = LayoutInflater.from(parent.context)
        .inflate(R.layout.form_style_material3_card_parent, parent, false) as? ViewGroup

    override fun onBindParentViewHolder(
        manager: BaseFormManager, holder: FormViewHolder, item: BaseForm
    ) {
        (holder.itemView as? MaterialCardView)?.apply {
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
            if (item.topBoundary == FormBoundaryType.NONE) {
                shapeAppearance.setTopLeftCornerSize(0f).setTopRightCornerSize(0f)
            }
            if (item.bottomBoundary == FormBoundaryType.NONE) {
                shapeAppearance.setBottomLeftCornerSize(0f).setBottomRightCornerSize(0f)
            }
            shapeAppearanceModel = shapeAppearance.build()
            updateLayoutParams<RecyclerView.LayoutParams> {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                topMargin = when (item.topBoundary) {
                    FormBoundaryType.NONE -> 0
                    FormBoundaryType.LOCAL -> partVertical
                    FormBoundaryType.GLOBAL -> partVerticalEdge
                }
                bottomMargin = when (item.bottomBoundary) {
                    FormBoundaryType.NONE -> 0
                    FormBoundaryType.LOCAL -> partVertical
                    FormBoundaryType.GLOBAL -> partVerticalEdge
                }
                val recyclerViewWidth = manager.recyclerView?.width ?: 0
                if (recyclerViewWidth > manager.itemMaxWidth) {
                    marginStart = (recyclerViewWidth - manager.itemMaxWidth) / 2
                    marginEnd = (recyclerViewWidth - manager.itemMaxWidth) / 2
                } else {
                    marginStart = partHorizontal
                    marginEnd = partHorizontal
                }
            }
            if (childCount > 0) {
                if (item is FormGroupTitle) return@apply
                getChildAt(0).updatePaddingRelative(
                    top = if (item.topBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize,
                    bottom = if (item.bottomBoundary == FormBoundaryType.NONE) 0 else manager.itemVerticalEdgeSize - manager.itemVerticalSize
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