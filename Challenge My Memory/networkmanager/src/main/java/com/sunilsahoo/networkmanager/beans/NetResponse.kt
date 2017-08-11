/**
 * Response info contains inventory list, request id, response code, sync
 * completed status
 */
package com.sunilsahoo.networkmanager.beans

class NetResponse {

    var requestID: Int = 0
    private val responseData: Any? = null
    var responseCode: Int = 0
    /**
     * serverID = If add/update/delete status is success, server responds the
     * server id
     */
    private val serverID = ""

    companion object {

        val TAG = NetResponse::class.java!!.getName()
    }
}
