package com.chooongg.form

class FormDataVerificationException(
    val partName: CharSequence?,
    val field: String?,
    val name: CharSequence?
) : RuntimeException(name?.toString())