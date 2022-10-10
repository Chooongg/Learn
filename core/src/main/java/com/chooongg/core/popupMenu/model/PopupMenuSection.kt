package com.chooongg.core.popupMenu.model

class PopupMenuSection internal constructor() {
    var title: CharSequence? = null
    var items: ArrayList<BasePopupMenuItem> = ArrayList()

    fun item(block: PopupMenuItem.() -> Unit) {
        items.add(PopupMenuItem().apply(block))
    }
}