package com.chooongg.form.bean

import android.content.Context
import com.chooongg.form.BaseFormManager
import com.chooongg.form.FormDataVerificationException
import com.chooongg.form.enum.FormOutPutStyle
import com.chooongg.form.enum.FormOutputMode
import com.chooongg.form.enum.FormVisibilityMode
import org.json.JSONArray
import org.json.JSONObject

abstract class BaseMultipleOptionForm(type: Int, name: CharSequence, field: String?) :
    BaseOptionForm(type, name, field) {

    var separator: String = ","

    @Deprecated("弃用")
    override var content: CharSequence? = null

    val selectedKey = HashSet<CharSequence>()

    var outPutStyle: FormOutPutStyle = FormOutPutStyle.ARRAY

    override fun transformContent(context: Context): CharSequence? {
        if (selectedKey.isEmpty()) return null
        if (options.isNullOrEmpty()) return null
        val selected = ArrayList<CharSequence>()
        options!!.forEach { if (selectedKey.contains(it.getKey())) selected.add(it.getValue()) }
        return buildString {
            for (i in 0 until selected.size) {
                if (i != 0) append(' ')
                append(selected[i])
            }
        }
    }

    @Throws(FormDataVerificationException::class)
    override fun checkDataCorrectness(manager: BaseFormManager) {
        if (isMust && selectedKey.isEmpty()) {
            if (outputMode == FormOutputMode.ALWAYS) {
                throw FormDataVerificationException(null, field, name)
            } else if (outputMode == FormOutputMode.ONLY_VISIBLE) {
                if (isVisible) {
                    if (visibilityMode == FormVisibilityMode.ALWAYS) {
                        throw FormDataVerificationException(null, field, name)
                    }
                }
            }
        }
    }

    override fun outputData(manager: BaseFormManager, json: JSONObject) {
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
    }
}