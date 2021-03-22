package com.example.newscase.data.model.api

import com.example.newscase.BuildConfig
import com.example.newscase.data.model.News
import retrofit2.Response
import retrofit2.http.GET

/**
 * API interface for getting news from remote
 * API Key is defined in BuildConfig
 */
interface NewsService {
    @GET("everything?q=Apple&from=2021-03-16&sortBy=popularity&apiKey=${BuildConfig.API_KEY}")
    suspend fun getNews(): Response<News>
}