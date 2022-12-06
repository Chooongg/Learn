package com.chooongg.form.bean

import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormOutPutStyle
import org.json.JSONArray
import org.json.JSONObject

class FormCheckbox(name: CharSequence, field: String?) :
    BaseMultipleOptionForm(FormManager.TYPE_CHECKBOX, name, field) {

    var outPutStyle: FormOutPutStyle = FormOutPutStyle.ARRAY

    override fun outputData(manager: FormManager, json: JSONObject) {
        if (field != null && selectedKey.isNotEmpty()) {
            when (outPutStyle) {
                FormOutPutStyle.SEPARATOR -> json.put(field!!, buildString {
                    val array = selectedKey.toArray()
                    for (i in array.indices) {
                        if (i != 0) append(separator)
                        append(array[i])
                    }
                })
                FormOutPutStyle.ARRAY -> {
                    val jsonArray = JSONArray()
                    selectedKey.forEach { jsonArray.put(it) }
                    json.put(field!!, jsonArray)
                }
            }
        }
        snapshotExtensionFieldAndContent().forEach {
            if (it.value != null) {
                json.put(it.key, it.value)
            }
        }
    }
}