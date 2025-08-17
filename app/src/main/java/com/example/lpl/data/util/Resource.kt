package com.example.lpl.data.util

sealed class Resource<T>(val data: T?, val uiErrorMessage: String = "") {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(uiErrorMessage: String, data: T? = null) : Resource<T>(data = data, uiErrorMessage = uiErrorMessage)
}

sealed class UiState<T>(val data: T?) {
    class Loading<T>(data: T? = null) : UiState<T>(data = data)
    class Loaded<T>(data: T) : UiState<T>(data)
    data class Error<T>(val message: String, val responseCode: Int = 0) : UiState<T>(null)
}