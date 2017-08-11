package com.sunilsahoo.networkmanager

import android.util.Log

import com.sunilsahoo.networkmanager.beans.NetRequest
import com.sunilsahoo.networkmanager.listeners.RequestCallback

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by sunilkumarsahoo on 7/4/16.
 */
class RequestManager {
    private var httpHandler: HttpHandler? = null
    private var cancelRequest: Boolean = false
    private var currentScreen: RequestCallback? = null
    private val executor = Executors.newSingleThreadExecutor()

    fun execute(request: NetRequest?) {
        if (request != null) {
            try {
                httpHandler = HttpHandler(request)
                executor.execute(httpHandler!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


    /**
     * Cancel the request being sent.
     */
    @Synchronized fun cancel() {
        Log.e("Cancel request", "")
        cancelRequest = true
        if (httpHandler != null) {
            httpHandler!!.cancel(true)
        }

        if (currentScreen != null) {
            currentScreen!!.cancelRequest(false)
        }
        // Update the UI in full view screen

        currentScreen = null
        httpHandler = null

    }

    companion object {

        val INSTANCE = RequestManager()
    }

}
