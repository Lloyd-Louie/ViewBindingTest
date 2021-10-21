package com.example.multilanguage.utils

import com.datatheorem.android.trustkit.pinning.OkHttp3Helper
import com.example.multilanguage.BuildConfig
import com.example.multilanguage.model.ErrorResponse
import com.example.multilanguage.model.ServerError
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.facebook.stetho.server.http.HttpStatus
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.EOFException
import java.net.SocketTimeoutException
import java.security.spec.PSSParameterSpec.DEFAULT
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLHandshakeException
/**
 * Error Handling
 */
fun decodeUnknownError(throwable: Throwable): String {
    return when (throwable) {
        is JsonSyntaxException -> {
            "We\\'re not able to process your request\\n right now. Please try again later."
        }
        else -> {
            "An unexpected error occurred \nSorry for the inconvenience."
        }
    }
}

fun decodeNetworkError(throwable: Throwable): String {
    return when (throwable) {
        is EOFException -> {
            "No response from server."
        }
        is SocketTimeoutException -> {
            "Server connection timeout."
        }
        is OutOfMemoryError -> {
            "Not enough memory."
        }
        is SSLHandshakeException -> {
            "Invalid SSL Certificate."
        }
        else -> {
            "You're currently offline! Please check your internet connection and try again."
        }
    }
}


//fun decodeErrorResponse(errorResponse: ErrorResponse?, code: Int): ServerError {
//    var mHttpCode: Int? = null
//
//    var mTitle: String? = null
//    var mDetails: String? = null
//    return ServerError (mHttpCode,mTitle,mDetails)
//}


/**
 * Retrofit
 */
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
        .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        //.sslSocketFactory(OkHttp3Helper.getSSLSocketFactory(), OkHttp3Helper.getTrustManager())
        //.addInterceptor(OkHttp3Helper.getPinningInterceptor())
        .followRedirects(false)
        .followSslRedirects(false)

    return if (BuildConfig.DEBUG) {
        client.addInterceptor(
            LoggingInterceptor.Builder()
                .request(GLOBAL_TAG + REQUEST_TAG)
                .response(GLOBAL_TAG + RESPONSE_TAG)
                .log(Platform.INFO)
                .setLevel(Level.BASIC)
                .build()
        ).addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Connection", "close")
            requestBuilder.addHeader("Accept", "application/json")
            requestBuilder.addHeader("Content-Type", "application/json")
            requestBuilder.addHeader("Authorization", "Bearer ")
            val request = requestBuilder.method(original.method, original.body).build()
            return@addInterceptor it.proceed(request)
        }.addNetworkInterceptor(StethoInterceptor()).build()
    } else {
        client.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader("Connection", "close")
            requestBuilder.addHeader("Accept", "application/json")
            requestBuilder.addHeader("Content-Type", "application/json")
            requestBuilder.addHeader("Authorization", "Bearer ")
            val request = requestBuilder.method(original.method, original.body).build()
            return@addInterceptor it.proceed(request)
        }.build()
    }
}
inline fun <reified T> createApiService(
    okHttpClient: OkHttpClient, factory: CallAdapter.Factory, baseUrl: String
): T {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build().create(T::class.java)
}


