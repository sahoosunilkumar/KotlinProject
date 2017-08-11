package com.sunilsahoo.networkmanager.utils


object NetworkConstants {

    val EOF: Byte = -1
    val DEFAULT_VALUE_OF_INT: Byte = 0
    val ACTION_CONNECTIVITY_LISTENER = "android.net" + ".conn.CONNECTIVITY_CHANGE"
    var URL_SEPARATOR = "/"

    /**
     * Description : Stores all types of response codes
     */
    object ResponseCodes {
        val SUCCESS = 200
    }

    /**
     * Description	: Stores maximum and minimum threshold value for all tasks
     */
    object Limits {
        /**
         * No of byte that a device will receive at one chunk
         */
        val CHUNK_SIZE = 1024
        /**
         * Http retry interval in milliseconds = 30 seconds
         */
        val RETRY_INTERVAL = 30 * 1000
        /**
         * Http connection time out in milli seconds ie 5 seconds less than
         * retry interval
         */
        val TIME_OUT = RETRY_INTERVAL - 5 * 1000
    }

}
