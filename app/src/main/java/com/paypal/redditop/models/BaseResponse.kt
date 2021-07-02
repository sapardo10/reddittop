package com.paypal.redditop.models

data class BaseResponse<T>(
    val after: String?,
    val before: String?,
    val data: T
)