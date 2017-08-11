/**
 * Stores Request infor required to send request
 */
package com.sunilsahoo.networkmanager.beans

import com.sunilsahoo.networkmanager.listeners.RequestCallback

class NetRequest {

    var requestID: Int = 0
    var httpOperationType: String? = null
    var url: String? = null
    var requestString: String? = null
    var callback: RequestCallback? = null
}
