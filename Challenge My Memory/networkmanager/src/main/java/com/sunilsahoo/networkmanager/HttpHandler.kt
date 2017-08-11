package com.sunilsahoo.networkmanager

import android.util.Log

import com.sunilsahoo.networkmanager.beans.NetRequest
import com.sunilsahoo.networkmanager.utils.NetworkConstants

import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * This class is used to create the http connections.

 * @author sunilkumar.sahoo
 */
class HttpHandler
/**
 * Constructor

 * @param
 */
(netRequest: NetRequest) : Runnable {
    private var cancel: Boolean = false
    private var connectStatus = false
    private var netRequest: NetRequest? = null
    private val requestID: Int

    init {
        this.netRequest = netRequest
        this.requestID = netRequest.requestID
    }

    /**
     * Trust every server - dont check for any certificate
     */
    private fun trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>,
                                            authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>,
                                            authType: String) {
            }
        })

        // Install the all-trusting trust manager
        try {
            val sc = SSLContext.getInstance("TLS")// ("TLS");
            sc.init(null, trustAllCerts, java.security.SecureRandom())
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.socketFactory)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * This method is used to create the http connection.
     */

    override fun run() {
        val out: OutputStream? = null
        var url: URL? = null
        var httpURLConnection: HttpURLConnection? = null
        val bis: BufferedInputStream? = null
        val baos: ByteArrayOutputStream? = null
        val dos: DataOutputStream? = null
        var buffer: StringBuffer? = StringBuffer()
        var responseData: String? = null
        var arrayInputStream: InputStream? = null
        cancel = false
        var respCode = -1

        try {
            Log.i(TAG, "URL :::" + netRequest!!.url)
            url = URL((netRequest as NetRequest).url)

            if (!cancel) {
                do {
                    trustAllHosts()
                    // respCode = connect(requestID, requestData);
                    // Create httpConnection
                    val hostnameVerifier = HostnameVerifier { _, _ -> true }
                    httpURLConnection = url
                            .openConnection() as HttpURLConnection
                    if (httpURLConnection is HttpsURLConnection) {
                        httpURLConnection.hostnameVerifier = hostnameVerifier
                    }

                    if (!cancel) {
                        httpURLConnection.requestMethod = (netRequest as NetRequest)
                                .httpOperationType
                        httpURLConnection.allowUserInteraction = true
                        httpURLConnection.useCaches = false
                        httpURLConnection.defaultUseCaches = false
                        if ("POST" == httpURLConnection.requestMethod) {
                            httpURLConnection.doInput = true
                            httpURLConnection.doOutput = true
                        }
                        writeData(httpURLConnection)

                        if (!cancel) {
                            respCode = httpURLConnection.responseCode
                        }
                    }
                    if (cancel) {
                        break
                    }
                } while (respCode == NetworkConstants.EOF.toInt() && !cancel)
            }

            Log.e("Response Code outside", respCode.toString())

            var data: ByteArray?
            if (!cancel) {
                // Log.e("Reading input stream", "Started Cancel:" + cancel);
                arrayInputStream = httpURLConnection!!.inputStream
                if (arrayInputStream != null) {
                    // bis = new
                    // BufferedInputStream(httpURLConnection.getInputStream());

                    val length = httpURLConnection.contentLength
                    var readLength = 0
                    var readBytes : Int
                    if (length < NetworkConstants.Limits.CHUNK_SIZE && length > NetworkConstants.DEFAULT_VALUE_OF_INT) {
                        data = ByteArray(length)
                    } else {
                        data = ByteArray(NetworkConstants.Limits.CHUNK_SIZE)
                    }
                    readBytes = arrayInputStream.read(data)
                    var dataRead: String?
                    while (readBytes != NetworkConstants.EOF.toInt() && !cancel) {
                        dataRead = String(data!!, NetworkConstants
                                .DEFAULT_VALUE_OF_INT.toInt(), readBytes)

                        buffer!!.append(dataRead)
                        readLength += readBytes
                        data = null
                        if (length > NetworkConstants.DEFAULT_VALUE_OF_INT && length - readLength < NetworkConstants.Limits
                                .CHUNK_SIZE) {
                            data = ByteArray(length - readLength)
                        } else {
                            data = ByteArray(NetworkConstants.Limits.CHUNK_SIZE)
                        }
                        if (cancel) {
                            break
                        }
                        readBytes = arrayInputStream.read(data)
                    }
                    responseData = buffer!!.toString()
                }

            }

        } catch (exp: Exception) {
            Log.e(TAG, "Exception in sending data network status :" + exp
                    .message)
            responseData = exp.message
            cancel = false
        } finally {
            if (!cancel) {
                if (responseData != null) {
                    notifyController(cancel, requestID, responseData, respCode)
                }
            }
            cleanup(arrayInputStream, httpURLConnection, out, baos, dos, bis, url)

        }
    }

    /**
     * writes data to url

     * @param httpURLConnection
     * *
     * @param out
     * *
     * @throws Exception
     */

    @Throws(Exception::class)
    private fun writeData(httpURLConnection: HttpURLConnection?) {
        var out: OutputStream?
        if (httpURLConnection != null) {
            // httpURLConnection.setRequestProperty("Connection",
            // "keep-alive");
            httpURLConnection?.setRequestProperty(
                    "Content-Type", "application/json")
            httpURLConnection.setRequestProperty(
                    "Content-Encoding", "UTF-8")
            httpURLConnection.readTimeout = NetworkConstants.Limits.TIME_OUT
            httpURLConnection.setRequestProperty(
                    "ConnectionTimeout", "" + NetworkConstants.Limits.TIME_OUT)

            // if (httpURLConnection != null)
            // httpURLConnection.setRequestProperty("Content-Length",
            // ""+ requestData.length);

        }

        if (httpURLConnection != null && !cancel) {
            // Log.e("httpURLConnection", "connect");
            connectStatus = false
            httpURLConnection.readTimeout = NetworkConstants.Limits.TIME_OUT
            httpURLConnection.connect()

            connectStatus = true
            // Log.e("httpURLConnection", "connect completed");
            if (httpURLConnection != null && cancel) {
                httpURLConnection.disconnect()
            }
        }

        if (httpURLConnection != null && !cancel
                && netRequest!!.requestString != null) {
            // Log.e("httpURLConnection", "getOutputStream");
            out = httpURLConnection.outputStream
            if (out != null
                    && (netRequest as NetRequest).requestString != null && httpURLConnection.requestMethod != "GET") {
                out.write(((netRequest as NetRequest).requestString as String)
                        .toByteArray())
                out.flush()
            }

        }
    }


    private fun cleanup(arrayInputStream: InputStream?, httpURLConnection: HttpURLConnection?, out: OutputStream?, baos: ByteArrayOutputStream?, dos: DataOutputStream?, bis: BufferedInputStream?, url: URL?) {
        var arrayInputStream = arrayInputStream
        var httpURLConnection = httpURLConnection
        var out = out
        var baos = baos
        var dos = dos
        var bis = bis
        var url = url
        try {
            // Log.e("finally", "Started Cancel:" + cancel);
            // Close the httpURLConnection.

            if (arrayInputStream != null) {
                arrayInputStream.close()
                arrayInputStream = null
            }
            if (httpURLConnection != null && connectStatus) {
                httpURLConnection.disconnect()
            }
            httpURLConnection = null
            if (out != null) {
                out.close()
                out = null
            }

            // Close the data output stream.
            if (dos != null) {
                dos.close()
            }
            dos = null
            // Close the byte array output stream.
            if (baos != null) {
                baos.close()
            }
            baos = null
            // Close the url
            if (url != null) {
                url = null
            }

            // Close the byte array output stream.
            if (bis != null) {
                bis.close()
            }
            bis = null
            cancel = false
        } catch (exp: Exception) {

        }

    }

    /**
     * notifies controller

     * @param cancel
     * *
     * @param requestID
     * *
     * @param response
     * *
     * @param responseCode
     */
    fun notifyController(cancel: Boolean, requestID: Int,
                         response: String, responseCode: Int) {
        if (!cancel) {
            if (this.netRequest != null && (this.netRequest as NetRequest).callback != null)
                (this.netRequest as NetRequest).callback?.onHttpResponseReceived(requestID,
                        response, responseCode)
        }
    }

    fun cancel(flag: Boolean) {
        cancel = true
    }

    companion object {
        private val TAG = HttpHandler::class.java!!.getName()
    }

}
