/**
 * Responsibility: Defines all constants used in controller
 */
package com.sunilsahoo.controller.utils


class ControllerConstants {

    /**
     * Description : All types of request codes which will be used for Http
     * request and request which will be used in application
     */
    interface RequestCodes {
        companion object {
            val GET_DEVICE_LSIT: Short = 171
        }

    }

    /**
     * Description : Stores all types of response codes
     */
    object ResponseCodes {
        val SUCCESS = 200
    }


    /**
     * Description : This class contains all types of Http methods which
     * will be
     * used in our application
     */
    interface HttpMethods {
        companion object {
            val HTTP_GET = "GET"
            val HTTP_POST = "POST"
            val HTTP_PUT = "PUT"
            val HTTP_DELETE = "DELETE"
        }
    }

}
