package com.example.newscase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.newscase.repositories.NewsRepository
import com.example.newscase.viewmodels.MainViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // Meta
    private lateinit var repository: NewsRepository
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Timber Logger
        Timber.plant(Timber.DebugTree())

        // ViewModel & Repository
        repository = NewsRepository(this)
        viewModel = MainViewModel(repository)

        // UI elements
        val newsButton: Button = findViewById(R.id.update_button)
        val articleRecyclerView: RecyclerView = findViewById(R.id.article_recycleview)

        viewModel.news.observe(this, { response ->
            val newsData = response
            //TODO Handle data here
            if (newsData?.articles != null) {
            }
        })
    }
}