package com.chooongg.basic

import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.basic.mmkv.MMKVContainer
import com.chooongg.basic.mmkv.MMKVKey
import com.tencent.mmkv.MMKV

object LearnMMKV : MMKVContainer() {

    override fun createMMKV(): MMKV = MMKV.mmkvWithID("chooongg-learn")

    internal object DayNightMode :
        MMKVKey<Int>(getMMKV(), "day_night_mode", AppCompatDelegate.MODE_NIGHT_NO)
}