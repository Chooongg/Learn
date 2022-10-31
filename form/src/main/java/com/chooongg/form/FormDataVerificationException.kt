package com.chooongg.form

class FormDataVerificationException(val name: String?, info: String?) : RuntimeException(info)