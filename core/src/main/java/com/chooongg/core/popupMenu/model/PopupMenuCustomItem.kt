package com.chooongg.core.popupMenu.model

import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat

class PopupMenuCustomItem : BasePopupMenuItem() {
    @LayoutRes
    var layoutResId: Int = ResourcesCompat.ID_NULL
}