package com.emilio.oompas.models

data class ApiResponse(
    val current: Int,
    val total: Int,
    val results: MutableList<Member>
)
