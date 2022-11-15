package com.chooongg.form.bean

import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormAreaMode

class FormAddress(name: CharSequence) : BaseForm(FormManager.TYPE_ADDRESS, name) {

    override var seeType: Int = FormManager.TYPE_TEXT

    var areaMode: FormAreaMode = FormAreaMode.PROVINCE_CITY_AREA_ADDRESS

    /**
     * 经度参数
     */
    var longitudeField: String? = null

    /**
     * 经度
     */
    var longitude: CharSequence?
        get() = if (longitudeField != null) getExtensionContent(longitudeField!!) else null
        set(value) {
            if (longitudeField != null) setExtensionContent(longitudeField!!, value)
        }

    /**
     * 纬度参数
     */
    var latitudeField: String? = null

    /**
     * 纬度
     */
    var latitude: CharSequence?
        get() = if (latitudeField != null) getExtensionContent(latitudeField!!) else null
        set(value) {
            if (latitudeField != null) setExtensionContent(latitudeField!!, value)
        }

    /**
     * 省份参数
     */
    var provinceField: String? = null

    /**
     * 省份名称
     */
    var province: CharSequence?
        get() = if (provinceField != null) getExtensionContent(provinceField!!) else null
        set(value) {
            if (provinceField != null) setExtensionContent(provinceField!!, value)
        }

    /**
     * 城市参数
     */
    var cityField: String? = null

    /**
     * 城市名称
     */
    var city: CharSequence?
        get() = if (cityField != null) getExtensionContent(cityField!!) else null
        set(value) {
            if (cityField != null) setExtensionContent(cityField!!, value)
        }

    /**
     * 地区参数
     */
    var areaField: String? = null

    /**
     * 地区名称
     * @see areaMode == FormAreaMode.PROVINCE_CITY_AREA_ADDRESS : 单地区
     */
    var area: CharSequence?
        get() = if (areaField != null) getExtensionContent(areaField!!) else null
        set(value) {
            if (areaField != null) setExtensionContent(areaField!!, value)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as FormAddress

        if (seeType != other.seeType) return false
        if (areaMode != other.areaMode) return false
        if (longitudeField != other.longitudeField) return false
        if (latitudeField != other.latitudeField) return false
        if (provinceField != other.provinceField) return false
        if (cityField != other.cityField) return false
        if (areaField != other.areaField) return false

        return true
    }

    override fun hashCode(): Int {
        var result = seeType
        result = 31 * result + areaMode.hashCode()
        result = 31 * result + (longitudeField?.hashCode() ?: 0)
        result = 31 * result + (latitudeField?.hashCode() ?: 0)
        result = 31 * result + (provinceField?.hashCode() ?: 0)
        result = 31 * result + (cityField?.hashCode() ?: 0)
        result = 31 * result + (areaField?.hashCode() ?: 0)
        return result
    }


}