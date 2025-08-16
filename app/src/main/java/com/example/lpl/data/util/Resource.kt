package com.example.lpl.data.util

sealed class Resource<T>(val data: T?, val lastResponse: Boolean = true, val actualErrorReturnedFromBackend: String = "", val uiErrorMessage: String = "", val responseCode: Int = 0) {
    class Success<T>(data: T?, lastResponse: Boolean = true, responseCode: Int = 0) : Resource<T>(data = data, lastResponse = lastResponse, responseCode = responseCode)
    class Error<T>(uiErrorMessage: String, actualErrorReturnedFromBackend: String = "", data: T? = null, responseCode: Int = 0) : Resource<T>(data = data, actualErrorReturnedFromBackend = actualErrorReturnedFromBackend, uiErrorMessage = uiErrorMessage, responseCode = responseCode)
}

sealed class UiState<T>(val data: T?) {
    class InitialState<T> : UiState<T>(null)
    class Loading<T>(data: T? = null) : UiState<T>(data = data)
    class Loaded<T>(data: T) : UiState<T>(data)
    class Finished<T> : UiState<T>(null)
    data class Success<T>(val response: T? = null, val responseCode: Int = 0) : UiState<T>(response)
    data class Error<T>(val message: String, val responseCode: Int = 0) : UiState<T>(null)

    companion object {
        const val initialState = "initialState"
        const val loading = "loading"
        const val loaded = "loaded"
        const val success = "success"
        const val finished = "finished"
        const val error = "error"
    }
}