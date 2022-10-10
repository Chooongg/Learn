package com.chooongg.core.popupMenu

fun popupMenu(block: PopupMenuBuilder.() -> Unit) = PopupMenuBuilder().apply(block)