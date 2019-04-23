package com.filter.android.model.remote

import retrofit2.Response
import java.io.IOException

class ApiResponse<T> {

    private var code: Int = 0//default
    var body: T? = null
    var errMsg: String? = null

    //HTTP status codes, HTTP/1.1 standard (RFC 7231)
    //1xx (Informational): The request was received, continuing process
    //2xx (Successful): The request was successfully received, understood, and accepted
    //3xx (Redirection): Further action needs to be taken in order to complete the request
    //4xx (Client Error): The request contains bad syntax or cannot be fulfilled
    //5xx (Server Error): The server failed to fulfill an apparently valid request
    val isSuccessful: Boolean
        get() = code >= 200 && code < 300

    constructor(response: Response<T>) {
        code = response.code()

        if (isSuccessful) {
            handleSuccessApiResponse(response)
        } else {
            handleErrorApiResponse(response)
        }
    }

    constructor(it: Throwable) {
        this.code = 500
        this.body = null
        this.errMsg = it.message
    }

    private fun handleSuccessApiResponse(response: Response<T>) {
        body = response.body()
        errMsg = null//default is null
    }

    private fun handleErrorApiResponse(response: Response<T>) {

        var msg: String? = null

        if (response.errorBody() != null) {
            try {
                msg = response.errorBody()!!.string()
            } catch (e: IOException) {
                e.printStackTrace()//Log here
            }
        }

        if (msg == null || msg.trim().isEmpty()) {
            msg = response.message()
        }

        body = null
        errMsg = msg
    }
}