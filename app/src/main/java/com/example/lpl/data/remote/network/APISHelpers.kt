package com.example.lpl.data.remote.network


import android.util.Log
import com.example.lpl.common.ApiResponseErrors
import com.example.lpl.data.util.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class APISHelpers @Inject constructor() {


    suspend fun <T> callApi(requestFunc: suspend () -> Response<T>): Resource<T> {
        val tag = "APIHelper"
        val response: Response<T>

        //if (wifiStatus.value == ConnectivityObserver.WifiStatus.Available) {

        try {
            response = requestFunc.invoke()
            return if (response.isSuccessful) {
                if (response.body() != null) {
                    Resource.Success(data = response.body(), responseCode = response.code())
                } else {
                    Resource.Error(
                        uiErrorMessage = "An internal error occurred",
                        responseCode = HttpsURLConnection.HTTP_FORBIDDEN
                    )
                }
            } else {
                val responseError = response.code().toString() + response.errorBody()?.string()

                Log.e(tag, responseError)

                when {
                    response.code() == HttpsURLConnection.HTTP_BAD_REQUEST -> {
                        Resource.Error(
                            actualErrorReturnedFromBackend = responseError,
                            uiErrorMessage = "An internal error occurred",
                            responseCode = response.code()
                        )
                    }

                    response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED -> {

                        Resource.Error(
                            actualErrorReturnedFromBackend = response.message(),
                            uiErrorMessage = "Unauthorized",
                            responseCode = response.code()
                        )
                    }

                    response.code() == HttpsURLConnection.HTTP_FORBIDDEN -> {

                        Resource.Error(
                            actualErrorReturnedFromBackend = responseError,
                            uiErrorMessage = "You do not have permission for this action. Contact your office.",
                            responseCode = response.code()
                        )
                    }

                    else -> {
                        Resource.Error(
                            actualErrorReturnedFromBackend = responseError,
                            uiErrorMessage = "Error: ${response.message()} Try again later! *${responseError}",
                            responseCode = response.code()
                        )
                    }
                }

            }
        } catch (throwable: Throwable) {
            return when (throwable) {
                is HttpException -> {
                    Log.e(tag, "HttpException: " + throwable.message.toString())

                    Resource.Error(
                        uiErrorMessage = "Server Error! Try again later",
                        actualErrorReturnedFromBackend = "${ApiResponseErrors.EXCEPTION.errorMessage}: ${throwable.message.toString()}"
                    )
                }

                is IOException -> {
                    Log.e(tag, "IOException: " + throwable.message.toString())

                    Resource.Error(
                        uiErrorMessage = "Check your internet connection",
                        actualErrorReturnedFromBackend = "${ApiResponseErrors.EXCEPTION.errorMessage}: ${throwable.message.toString()}"
                    )
                }

                is TimeoutException -> {
                    Log.e(tag, "TimeoutException:" + throwable.message.toString())

                    Resource.Error(
                        uiErrorMessage = ApiResponseErrors.TIMEOUT_EXCEPTION.errorMessage,
                        actualErrorReturnedFromBackend = ApiResponseErrors.TIMEOUT_EXCEPTION.errorMessage
                    )
                }

                else -> {
                    Log.e(tag, "Exception:" + throwable.message.toString())

                    Resource.Error(
                        uiErrorMessage = "Something went wrong",
                        actualErrorReturnedFromBackend = "${ApiResponseErrors.EXCEPTION.errorMessage}: ${throwable.message.toString()}"
                    )
                }
            }
        }
//    } else {
//        return Resource.Error(uiErrorMessage = "Check your internet connection")
//    }
    }

}