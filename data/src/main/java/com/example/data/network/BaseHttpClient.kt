package com.example.data.network

import android.content.Context
import com.example.data.BuildConfig
import com.example.data.config.HttpClientConfiguration
import com.example.data.network.interceptors.CacheInterceptor
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class BaseHttpClient(
    private val context: Context,
    private val configuration: HttpClientConfiguration
) {
    fun <T>createRetrofitObject(retrofitInterface: Class<T>): T =
        getRetrofitClient().create(retrofitInterface)

    private fun getRetrofitClient(): Retrofit = Retrofit.Builder()
        .baseUrl(configuration.baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(getMoshiAdapter()).asLenient())
        .client(setUpOkHttpClient())
        .build()

    private fun setUpOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(configuration.timeOutDuration, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(configuration.timeOutDuration, TimeUnit.SECONDS)

        /* Adds a cache to be used if any calls contain cache related headers.
        If the response from the server has these headers, we don't need an interceptor */
        if (configuration.hasCaching) {
            val cacheSize = 50 * 1024 * 1024L // 50 MB
            val cache = context.cacheDir?.let { Cache(it, cacheSize) }
            httpClientBuilder.cache(cache)

            // add cache header injection interceptor
            httpClientBuilder.addNetworkInterceptor(CacheInterceptor())
        }


        configuration.networkInterceptors.forEach {
            httpClientBuilder.addNetworkInterceptor(it)
        }

        // add last to correctly log everything.
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClientBuilder.addInterceptor(logging)
        }

        return httpClientBuilder.build()
    }

    private fun getMoshiAdapter() = Moshi.Builder()
        // Add custom adapters here
        .build()
}
