/**
 * Network status
 */
package com.sunilsahoo.networkmanager.beans

class NetworkStatus private constructor() {
    var isNetworkAvail = false
    var isWifiAvail = false

    private object NetworkStatusInstanceHolder {
        val INSTANCE = NetworkStatus()
    }

    companion object {

        /**
         * Creates instance if not exists

         * @return
         */
        val instance: NetworkStatus
            get() = NetworkStatusInstanceHolder.INSTANCE
    }
    /**
     * Sets network status both for wifi connectivity and network availability
     * @param context
     */


}
