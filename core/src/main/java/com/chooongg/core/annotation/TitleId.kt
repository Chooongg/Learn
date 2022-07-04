package com.chooongg.core.annotation

import androidx.annotation.StringRes

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TitleId(@StringRes val value: Int)
