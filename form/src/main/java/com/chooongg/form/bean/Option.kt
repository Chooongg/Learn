package com.chooongg.form.bean

interface Option {
    fun getKey(): CharSequence
    fun getValue(): CharSequence
    fun isShow(): Boolean
}