package com.chooongg.form.bean

import android.content.Context
import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormOutPutStyle
import org.json.JSONArray
import org.json.JSONObject

abstract class BaseMultipleOptionForm(type: Int, name: CharSequence, field: String?) :
    BaseOptionForm(type, name, field) {

    var separator: String = ","

    @Deprecated("弃用")
    override var content: CharSequence? = null

    val selectedKey = HashSet<CharSequence>()

    var outPutStyle:FormOutPutStyle = FormOutPutStyle.ARRAY

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

    override fun outputData(manager: FormManager, json: JSONObject) {
        if (field != null && selectedKey.isNotEmpty()) {
            when(outPutStyle){
                FormOutPutStyle.SEPARATOR->{
                    val selected = selectedKey.toTypedArray()
                    json.put(field!!,buildString {
                        for (i in selected.indices) {
                            if (i != 0) append(separator)
                            append(selected[i])
                        }
                    })
                }
                FormOutPutStyle.ARRAY->{
                    val jsonArray = JSONArray()
                    selectedKey.forEach {
                        jsonArray.put(it)
                    }
                    json.putOpt(field,jsonArray)
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