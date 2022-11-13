package com.chooongg.form.bean

import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormAreaMode

class FormAddress(name: CharSequence) : BaseForm(FormManager.TYPE_ADDRESS, name) {

    override var seeType: Int = FormManager.TYPE_TEXT

    var areaMode: FormAreaMode = FormAreaMode.PROVINCE_CITY_AREA_ADDRESS

    /**
     * 省份 Id
     */
    var provinceId: CharSequence?
        get() = getExtensionContent("province_id")
        set(value) = setExtensionContent("province_id", value)

    /**
     * 省份名称
     */
    var provinceText: CharSequence?
        get() = getExtensionContent("province_text")
        set(value) = setExtensionContent("province_text", value)

    /**
     * 城市 Id
     */
    var cityId: CharSequence?
        get() = getExtensionContent("city_id")
        set(value) = setExtensionContent("city_id", value)

    /**
     * 城市名称
     */
    var cityText: CharSequence?
        get() = getExtensionContent("city_text")
        set(value) = setExtensionContent("city_text", value)

    /**
     * 地区 Id
     */
    var areaId: CharSequence?
        get() = getExtensionContent("area_id")
        set(value) = setExtensionContent("area_id", value)

    /**
     * 地区名称
     * @see areaMode == FormAreaMode.PROVINCE_CITY_AREA_ADDRESS : 单地区
     */
    var areaText: CharSequence?
        get() = getExtensionContent("area_text")
        set(value) = setExtensionContent("area_text", value)

    /**
     * 详细地址 Id
     */
    var addressId: String?
        get() = super.field
        set(value) {
            super.field = value
        }

    /**
     * 详细地址名称
     */
    var addressText: CharSequence?
        get() = super.content
        set(value) {
            super.content = value
        }
}