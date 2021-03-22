package com.example.newscase.repositories

import android.content.Context
import com.example.newscase.data.model.News
import com.example.newscase.data.model.api.RetrofitBuilder
import retrofit2.Response
import timber.log.Timber

/**
 * Repository: Implements repository pattern for abstraction
 * Used to access both remote & local data
 */
class NewsRepository(context: Context) {

    suspend fun getNews(): Response<News> {
        Timber.i("Repo: Fetching news")
        return RetrofitBuilder.RetrofitBuilder.newsService.getNews()
    }

}