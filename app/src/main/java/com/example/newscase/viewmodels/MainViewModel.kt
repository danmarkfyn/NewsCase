package com.example.newscase.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newscase.data.model.News
import com.example.newscase.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * ViewModel for main activity. Functions as mediator between UI and Data
 */
class MainViewModel(private val repository: NewsRepository) : ViewModel() {
    // Observable data
    val news: MutableLiveData<News> = MutableLiveData()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val data = repository.getPersistentNews()
                if (data != null) {
                    Timber.i("DB data: $data")
                    news.postValue(data)
                } else {
                    getNews()
                }
            }
        }
    }

    /**
     * Getting news from remote sources
     */
    fun getNews() = viewModelScope.launch {
        // Trying to fetch remote data (News)
        try {
            repository.getNews().let { response ->
                Timber.i("Attempting to fetch news")
                // Checks if respond returned with successful
                if (response.isSuccessful) {
                    val entry = response.body()
                    Timber.i("Fetch Successful")

                    // Save the News in DB
                    withContext(Dispatchers.IO) {
                        if (entry != null) {
                            news.value?.let { repository.addNews(it) }
                        }
                    }
                    // Update LiveData
                    news.postValue(entry)
                } else {
                    Timber.i("Failure when attempting to fetch news")
                }
            }
        } catch (e: Exception) {
            Timber.d("Error while attempting to fetch news: $e")
        }
    }

    /**
     * Getting news from persistent sources
     */
    fun getPersistentNews() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val data = repository.getPersistentNews()
            Timber.i("DB data: $data")
        }
    }

}