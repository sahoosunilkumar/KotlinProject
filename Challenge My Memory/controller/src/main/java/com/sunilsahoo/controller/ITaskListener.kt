package com.sunilsahoo.controller

import com.sunilsahoo.controller.entity.RestResponse

/**
 * Created by sunilkumarsahoo on 7/5/16.
 */
interface ITaskListener {
    fun onSuccess(response: RestResponse)

    fun onError(error: String, code: Int)
}
