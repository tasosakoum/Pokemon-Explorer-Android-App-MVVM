package com.example.data.config

import okhttp3.Interceptor

data class HttpClientConfiguration(
    val baseUrl: String,
    val hasCaching: Boolean,
    val timeOutDuration: Long = 30,
    val networkInterceptors: List<Interceptor> = listOf(),
)
