package com.chooongg.core.popupMenu2.model

class PopupMenuSection internal constructor() {
    var title: CharSequence? = null
    var isForceShowIcon: Boolean? = null
    var items: ArrayList<BasePopupMenuItem> = ArrayList()
}