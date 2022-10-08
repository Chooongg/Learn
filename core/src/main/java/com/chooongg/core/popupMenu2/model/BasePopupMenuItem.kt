package com.chooongg.core.popupMenu2.model

import android.view.View

abstract class BasePopupMenuItem {
    open var autoDismiss: Boolean = true
    open var onSelectedCallback: (() -> Unit)? = null
    open var onViewBoundCallback: ((View) -> Unit)? = null
}