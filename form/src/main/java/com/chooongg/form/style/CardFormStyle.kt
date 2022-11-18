package com.chooongg.form.style

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.basic.ext.*
import com.chooongg.form.FormManager
import com.chooongg.form.FormViewHolder
import com.chooongg.form.R
import com.chooongg.form.bean.BaseForm
import com.chooongg.form.bean.FormGroupTitle
import com.chooongg.form.enum.FormBoundaryType
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textview.MaterialTextView

class CardFormStyle : FormStyle(100) {

    override fun createItemParentView(parent: ViewGroup) = MaterialCardView(
        parent.context, null, com.google.android.material.R.attr.materialCardViewElevatedStyle
    ).apply {
        rippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
        layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindParentViewHolder(holder: FormViewHolder, item: BaseForm) {
        val cardView = holder.itemView as MaterialCardView
        val shapeAppearance = ShapeAppearanceModel.builder(
            holder.itemView.context, holder.itemView.attrResourcesId(
                com.google.android.material.R.attr.shapeAppearanceCornerMedium, 0
            ), 0
        )
        if (item.adapterTopBoundary == FormBoundaryType.NONE) {
            shapeAppearance.setTopLeftCornerSize(0f).setTopRightCornerSize(0f)
        }
        if (item.adapterBottomBoundary == FormBoundaryType.NONE) {
            shapeAppearance.setBottomLeftCornerSize(0f).setBottomRightCornerSize(0f)
        }
        cardView.shapeAppearanceModel = shapeAppearance.build()
        cardView.updateLayoutParams<RecyclerView.LayoutParams> {
            marginStart = holder.itemView.resDimensionPixelSize(R.dimen.formPartHorizontal)
            marginEnd = holder.itemView.resDimensionPixelSize(R.dimen.formPartHorizontal)
            topMargin = when (item.adapterTopBoundary) {
                FormBoundaryType.NONE -> 0
                FormBoundaryType.LOCAL -> holder.itemView.resDimensionPixelSize(R.dimen.formPartVertical)
                FormBoundaryType.GLOBAL -> holder.itemView.resDimensionPixelSize(R.dimen.formPartVerticalGlobal)
            }
            bottomMargin = when (item.adapterBottomBoundary) {
                FormBoundaryType.NONE -> 0
                FormBoundaryType.LOCAL -> holder.itemView.resDimensionPixelSize(R.dimen.formPartVertical)
                FormBoundaryType.GLOBAL -> holder.itemView.resDimensionPixelSize(R.dimen.formPartVerticalGlobal)
            }
        }
    }

    override fun getGroupTitleLayoutId() = R.layout.form_item_group_name_card

    override fun onBindGroupTitleHolder(
        manager: FormManager,
        holder: FormViewHolder,
        item: FormGroupTitle
    ) {
        with(holder.getView<ShapeableImageView>(R.id.form_iv_background)) {
            shapeAppearanceModel = (holder.itemView as MaterialCardView).shapeAppearanceModel
        }
        with(holder.getView<AppCompatImageView>(R.id.form_iv_icon)) {
            if (item.icon != null) {
                isEnabled = item.isEnabled
                imageTintList = item.iconTint
                setImageResource(item.icon!!)
                visible()
            } else gone()
        }
        with(holder.getView<MaterialTextView>(R.id.form_tv_name)) {
            isEnabled = item.isEnabled
            text = item.name
        }
    }
}