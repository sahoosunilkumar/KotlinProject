/**
 * Responsibility: abstract class for
 * 1) send request to server
 * 2) parse response depending on entity type
 * 3) send callback
 */
package com.sunilsahoo.controller

import android.util.Log

import com.sunilsahoo.controller.entity.RestResponse
import com.sunilsahoo.controller.utils.ControllerConstants
import com.sunilsahoo.networkmanager.listeners.RequestCallback
import com.sunilsahoo.networkmanager.RequestManager
import com.sunilsahoo.networkmanager.beans.NetRequest

/**
 * Created by sunilkumarsahoo on 7/5/16.
 */
abstract class Controller : RequestCallback {
    private var controller: Controller? = null
    private var request: NetRequest? = null
    private var taskListener: ITaskListener? = null
    private var entity: Class<*>? = null

    init {
        controller = this
    }

    fun execute(requestID: Int, url: String, httpMethod: String, requestString: String?, taskListener: ITaskListener) {
        if (request == null) {
            this.taskListener = taskListener
            request = NetRequest()
            request!!.requestID = requestID
            request!!.url = url
            request!!.httpOperationType = httpMethod
            if (request!!.httpOperationType == ControllerConstants
                    .HttpMethods.HTTP_PUT || request!!.httpOperationType == ControllerConstants.HttpMethods.HTTP_POST) {
                request!!.requestString = requestString
            }
            request!!.callback = this
            execute(request as NetRequest)
        } else {
            Log.i(TAG, "Other Request is in process")
        }
    }

    /**
     * Forwards the request to a thread.

     * @param
     */
    private fun execute(request: NetRequest) {
        RequestManager.INSTANCE.execute(request)
    }

    override fun onHttpResponseReceived(requestID: Int, response: String, responseCode: Int) {
        if (taskListener == null) {
            Log.d(TAG, "cant notify listener is null")
            return
        }
        if (responseCode == ControllerConstants.ResponseCodes.SUCCESS) {
            val responseObj = RestResponse()
            responseObj.response = prepareResponse(response)
            responseObj.requestID = requestID
            responseObj.responseCode = responseCode
            taskListener!!.onSuccess(responseObj)
        } else {
            taskListener!!.onError(response, responseCode)
        }
    }

    override fun cancelRequest(flag: Boolean) {
        Log.i(TAG, "cancel request")
        //TODO implementation pending
    }


    /**
     * set returned entity type
     * @param entity
     */
    fun setEntity(entity: Class<*>) {
        this.entity = entity
    }

    /**
     * parse the response depending on entity type
     * @param response
     * *
     * @return
     */
    private fun prepareResponse(response: String): Any {
        if (entity == null) {
            return response
        }
        return Parser.INSTANCE.parse(response, entity as Class<*>)
    }

    companion object {
        private val TAG = Controller::class.java.name
    }

}
