package com.example.newscase.repositories

import android.content.Context
import androidx.room.Room
import com.example.newscase.data.model.News
import com.example.newscase.data.api.RetrofitBuilder
import com.example.newscase.data.database.NewsDatabase
import retrofit2.Response
import timber.log.Timber

/**
 * Repository: Implements repository pattern for abstraction
 * Used to access both remote & local data
 */
class NewsRepository(context: Context) {

    private val newsDB: NewsDatabase by lazy {
        Room.databaseBuilder(context, NewsDatabase::class.java, "news-db").build()
    }

    /**
     * Used to fetch remote data (News)
     */
    suspend fun getNews(): Response<News> {
        Timber.i("Repo: Fetching news")
        return RetrofitBuilder.RetrofitBuilder.newsService.getNews()
    }

    /**
     * Used to add News to persistent storage (Room DB)
     */
    fun addNews(news: News) {
        val newDao = newsDB.newsDao()
        newDao.insertNews(news)
        Timber.i("Added news articles to db!")
    }

    /**
     * Used to delete News from persistent storage (Room DB)
     */
    fun deleteNews(news: News) {
        val newDao = newsDB.newsDao()
        newDao.delete(news)
        Timber.i("Deleted news from to db!")
    }

    /**
     * Used to get news from persistent storage (Room DB)
     */
    fun getPersistentNews(): News {
        val newDao = newsDB.newsDao()
        Timber.i("Getting entities from DB!")
        return newDao.getNews()
    }

}