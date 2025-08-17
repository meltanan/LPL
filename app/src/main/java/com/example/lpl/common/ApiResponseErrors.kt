package com.example.lpl.common

enum class ApiResponseErrors(val errorCode: Int, val errorMessage: String) {
    TIMEOUT_EXCEPTION(0,"Timeout error")
}