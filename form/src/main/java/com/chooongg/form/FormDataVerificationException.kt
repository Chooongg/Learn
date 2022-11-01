package com.chooongg.form

class FormDataVerificationException(val field: String?, info: String) : RuntimeException(info)