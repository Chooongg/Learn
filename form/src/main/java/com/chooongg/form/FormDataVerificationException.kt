package com.chooongg.form

class FormDataVerificationException(
    val groupName: CharSequence?,
    val field: String?,
    val name: CharSequence?
) : RuntimeException(name?.toString())