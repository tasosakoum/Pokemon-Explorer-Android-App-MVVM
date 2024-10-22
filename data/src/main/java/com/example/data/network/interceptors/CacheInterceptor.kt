package com.example.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val cacheLifetime = 60
internal const val CACHE_CONTROL = "Cache-Control"
internal const val NETWORK_CACHE = "public, max-age=$cacheLifetime"
internal const val FORCE_CACHE = "public, only-if-cached, max-stale=0"
const val NO_CACHE = "no-cache"

internal class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequestBuilder = request.newBuilder()
            .header(CACHE_CONTROL, FORCE_CACHE)

        val response = chain.proceed(newRequestBuilder.build())

        return response.newBuilder()
            .header(CACHE_CONTROL, NETWORK_CACHE)
            .removeHeader("Pragma")
            .build()
    }
}
