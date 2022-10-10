package com.chooongg.core.popupMenu.model

import android.view.View

abstract class BasePopupMenuItem {
    open var selectedAutoDismiss: Boolean = true
    internal var onSelectedCallback: (() -> Unit)? = null
    internal var onViewBindCallback: ((View) -> Unit)? = null

    fun onSelectedCallback(callback: () -> Unit) {
        onSelectedCallback = callback
    }

    fun onViewBindCallback(callback: (View) -> Unit) {
        onViewBindCallback = callback
    }
}