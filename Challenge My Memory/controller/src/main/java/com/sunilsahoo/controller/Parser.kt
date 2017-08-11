/**
 * Responsibilty: To initialize parser
 */
package com.sunilsahoo.controller

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader

import java.io.StringReader

/**
 * Created by sunilkumarsahoo on 7/5/16.
 */
class Parser private constructor() {

    init {
        initializeParser()
    }

    fun parse(response: String, type: Class<*>): Any {
        val reader = JsonReader(StringReader(response))
        reader.isLenient = true

        return gson!!.fromJson<Any>(reader, type)
    }

    private fun initializeParser() {
        gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    companion object {
        private var gson: Gson? = null

        val INSTANCE = Parser()
    }
}
