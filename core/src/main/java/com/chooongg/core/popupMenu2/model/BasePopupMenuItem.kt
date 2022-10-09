package com.chooongg.core.popupMenu2.model

import android.view.View

abstract class BasePopupMenuItem {
    open var selectedAutoDismiss: Boolean = true
    open var onSelectedCallback: (() -> Unit)? = null
    open var onViewBindCallback: ((View) -> Unit)? = null
}