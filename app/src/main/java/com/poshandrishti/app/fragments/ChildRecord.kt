
package com.poshandrishti.app.fragments

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

data class ChildRecord(
    val name: String,
    val dob: String,
    val gender: String,
    val weight: String,
    val height: String,
    val muac: String,
    val guardian: String,
    val mobile: String
)

object ChildStorage {

    private const val PREFS = "poshan_children"
    private const val KEY_CHILDREN = "children"

    fun getChildren(context: Context): MutableList<ChildRecord> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val array = JSONArray(prefs.getString(KEY_CHILDREN, "[]"))
        val children = mutableListOf<ChildRecord>()

        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)

            children.add(
                ChildRecord(
                    name = item.optString("name"),
                    dob = item.optString("dob"),
                    gender = item.optString("gender"),
                    weight = item.optString("weight"),
                    height = item.optString("height"),
                    muac = item.optString("muac"),
                    guardian = item.optString("guardian"),
                    mobile = item.optString("mobile")
                )
            )
        }

        return children
    }

    fun saveChild(context: Context, child: ChildRecord) {
        val children = getChildren(context)
        children.add(child)

        val array = JSONArray()

        children.forEach { record ->
            val item = JSONObject().apply {
                put("name", record.name)
                put("dob", record.dob)
                put("gender", record.gender)
                put("weight", record.weight)
                put("height", record.height)
                put("muac", record.muac)
                put("guardian", record.guardian)
                put("mobile", record.mobile)
            }
            array.put(item)
        }

        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_CHILDREN, array.toString())
            .apply()
    }
}
