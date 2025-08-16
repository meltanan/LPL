package com.example.lpl.data.remote.network

import android.net.http.HttpException
import android.util.Log
import com.example.lpl.data.util.Resource
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.net.ssl.HttpsURLConnection

/*
suspend fun <T> callApi(requestFunc: suspend () -> Response<T>): Resource<T> {
    val tag = "APIHelper"
    val response: Response<T>

    //if (wifiStatus.value == ConnectivityObserver.WifiStatus.Available) {

        try {
            response = requestFunc.invoke()
            return if (response.isSuccessful) {
                if (response.body() != null) {
                    Resource.Success(data = response.body(), responseCode =  response.code())
                } else {
                    Resource.Error(uiErrorMessage = "An internal error occurred", responseCode = HttpsURLConnection.HTTP_FORBIDDEN)
                }
            } else {
                val responseError = response.code().toString() + response.errorBody()?.string()

                logErrorMessageToEmbrace("API Error Message is ${response.message()}")
                Log.e(tag, responseError)
                logErrorMessageToEmbrace(responseError)

                //If company is wrong, 400 is returned. If username or password is wrong 401 is returned. Backend checks for company first.
                if (response.raw().request.url.toString().contains("Authenticate") && (response.code() == 400 || response.code() == 401))
                    Resource.Error(actualErrorReturnedFromBackend = responseError, uiErrorMessage = context.getString(R.string.invalid_login_information))

                else {
                    when {
                        response.code() == HttpsURLConnection.HTTP_BAD_REQUEST -> {
                            Resource.Error(
                                actualErrorReturnedFromBackend = responseError,
                                uiErrorMessage = "An internal error occurred",
                                responseCode =  response.code()
                            )
                        }

                        response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                            globalEventBus.postEvent(GlobalEvent.ApiEvent("Status-Code 401: Unauthorized", response.code()))
                            Resource.Error(actualErrorReturnedFromBackend = response.message(), uiErrorMessage = "Unauthorized", responseCode =  response.code())
                        }

                        response.code() == HttpsURLConnection.HTTP_FORBIDDEN  -> {
                            globalEventBus.postEvent(GlobalEvent.ApiEvent("You do not have permission for this action. Contact your office", response.code()))
                            Resource.Error(
                                actualErrorReturnedFromBackend = responseError,
                                uiErrorMessage = "You do not have permission for this action. Contact your office.",
                                responseCode =  response.code()
                            )
                        }

                        response.code() == SERVER_NOT_CONFIGURED_EXCEPTION && JSONObject(responseError).has("message") -> {
                            val message = JSONObject(responseError).getString("message")
                            Resource.Error(
                                actualErrorReturnedFromBackend = responseError,
                                uiErrorMessage = message,
                                responseCode =  response.code()
                            )
                        }

                        else -> {
                            Resource.Error(
                                actualErrorReturnedFromBackend = responseError,
                                uiErrorMessage = "Error: ${response.message()} Try again later! *${responseError}",
                                responseCode =  response.code()
                            )
                        }
                    }
                }
            }
        } catch (throwable: Throwable) {
            return when (throwable) {
                is HttpException -> {
                    Log.e(tag, "HttpException: " + throwable.message.toString())
                    logWarningMessageToEmbrace("API Call HttpException: ${throwable.message.toString()}")
                    Resource.Error(uiErrorMessage = "Server Error! Try again later", actualErrorReturnedFromBackend = "${ApiResponseErrors.EXCEPTION.errorMessage}: ${throwable.message.toString()}")
                }

                is IOException -> {
                    Log.e(tag, "IOException: " + throwable.message.toString())
                    logWarningMessageToEmbrace("API Call IOException: ${throwable.message.toString()}")
                    Resource.Error(uiErrorMessage = "Check your internet connection", actualErrorReturnedFromBackend = "${ApiResponseErrors.EXCEPTION.errorMessage}: ${throwable.message.toString()}")
                }

                is TimeoutException -> {
                    Log.e(tag, "TimeoutException:" + throwable.message.toString())
                    logWarningMessageToEmbrace("TimeoutException: ${throwable.message.toString()}")
                    Resource.Error(uiErrorMessage = ApiResponseErrors.TIMEOUT_EXCEPTION.errorMessage, actualErrorReturnedFromBackend = ApiResponseErrors.TIMEOUT_EXCEPTION.errorMessage)
                }

                else -> {
                    Log.e(tag, "Exception:" + throwable.message.toString())
                    logWarningMessageToEmbrace("API Call Exception: ${throwable.message.toString()}")
                    Resource.Error(uiErrorMessage = "Something went wrong", actualErrorReturnedFromBackend = "${ApiResponseErrors.EXCEPTION.errorMessage}: ${throwable.message.toString()}")
                }
            }
        }
//    } else {
//        return Resource.Error(uiErrorMessage = "Check your internet connection")
//    }
}*/