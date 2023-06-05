package ru.geekbrain.android.repository.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseInterceptor private constructor(): Interceptor{
    private var responseCode: Int =0

    companion object{
        val interceptor: BaseInterceptor
            get() = BaseInterceptor()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        responseCode = response.code
        return response
    }

    fun getResponseCode(): ServerResponseStatusCode =
        when(responseCode /100){
            1-> ServerResponseStatusCode.INFO
            2-> ServerResponseStatusCode.SUCCESS
            3-> ServerResponseStatusCode.REDIRECTION
            4-> ServerResponseStatusCode.CLIENT_ERROR
            5-> ServerResponseStatusCode.SERVER_ERROR
            else -> ServerResponseStatusCode.UNDEFINED_ERROR
        }


    enum class ServerResponseStatusCode {
        INFO,
        SUCCESS,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNDEFINED_ERROR
    }
}


