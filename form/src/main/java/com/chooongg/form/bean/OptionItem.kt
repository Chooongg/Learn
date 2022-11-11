package com.chooongg.form.bean

class OptionItem : Option {

    private val key: CharSequence
    private val value: CharSequence

    constructor(value: CharSequence) {
        this.key = value
        this.value = value
    }

    constructor(key: CharSequence, value: CharSequence) {
        this.key = key
        this.value = value
    }

    override fun getKey() = key
    override fun getValue() = value
}