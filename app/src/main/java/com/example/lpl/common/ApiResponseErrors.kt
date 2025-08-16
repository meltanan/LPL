package com.example.lpl.common

enum class ApiResponseErrors(val errorCode: Int, val errorMessage: String) {
    DUPLICATE_ERROR_USING_MESSAGE(400, "Duplicate"),
    //full error is: "Action Item can not be stepped into a step it is currently in.
    STEPPING_ERROR(400, "not be stepped into"),
    DUPLICATE_ERROR_USING_CODE(409, ""),
    TIMEOUT_EXCEPTION(0,"Timeout error"),
    EXCEPTION(0,"Exception")
}