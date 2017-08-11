/**
 * HTTP listener callback to UI
 */
package com.sunilsahoo.networkmanager.listeners

interface RequestCallback {

    fun onHttpResponseReceived(requestID: Int, response: String, responseCode: Int)

    fun cancelRequest(flag: Boolean)
}
