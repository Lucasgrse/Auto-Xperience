package com.example.xperience.adapter.controller.dto.response

import com.example.xperience.adapter.ErrorResponseField

data class ErrorResponse(
    var message: String?,
    var errors: List<ErrorResponseField>? = null
)