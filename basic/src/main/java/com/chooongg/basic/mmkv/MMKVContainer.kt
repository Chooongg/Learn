package com.chooongg.basic.mmkv

import com.tencent.mmkv.MMKV

abstract class MMKVContainer {

    private var mmkv: MMKV? = null

    abstract fun createMMKV(): MMKV

    fun getMMKV(): MMKV? {
        if (mmkv == null) {
            mmkv = createMMKV()
        }
        return mmkv
    }

    fun clear() {
        mmkv?.clearAll()
    }
}