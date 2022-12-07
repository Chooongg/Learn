package com.chooongg.form.bean

import com.chooongg.form.FormManager
import com.chooongg.form.enum.FormOutPutStyle
import org.json.JSONArray
import org.json.JSONObject

class FormCheckbox(name: CharSequence, field: String?) :
    BaseMultipleOptionForm(FormManager.TYPE_CHECKBOX, name, field) {
}