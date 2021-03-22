package com.example.newscase.data.api

import com.example.newscase.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    /**
     * Singleton RetrofitBuilder for making api calls
     */
    object RetrofitBuilder {
        private val retrofitBuilder: Retrofit.Builder by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().apply {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.HEADERS
                    })
                }.build())
        }

        // object for using the NewsService
        val newsService: NewsService by lazy {
            retrofitBuilder
                .build()
                .create(NewsService::class.java)
        }
    }
}