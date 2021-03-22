package com.example.newscase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscase.data.model.Article
import com.example.newscase.repositories.NewsRepository
import com.example.newscase.ui.adapters.ArticleAdapter
import com.example.newscase.ui.decorators.VerticalDecorator
import com.example.newscase.viewmodels.MainViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // Meta
    private lateinit var repository: NewsRepository
    private lateinit var viewModel: MainViewModel

    // Adapters
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Timber Logger
        Timber.plant(Timber.DebugTree())

        // ViewModel & Repository
        repository = NewsRepository(this)
        viewModel = MainViewModel(repository)

        // UI elements
        val updateButton: Button = findViewById(R.id.update_button)
        val articleRecyclerView: RecyclerView = findViewById(R.id.article_recycleview)
        val loadIcon: ProgressBar = findViewById(R.id.news_screen_loading)

        // Load icon for visual feedback on data fetching
        loadIcon.isVisible = true

        viewModel.news.observe(this, { response ->
            //TODO Handle data here
            if (response?.articles != null) {
                loadIcon.isVisible = false
                initEventRecyclerView(this, articleRecyclerView)
                submitNews(response.articles)
            }
        })

        updateButton.setOnClickListener {
            loadIcon.isVisible = true
            initEventRecyclerView(this, articleRecyclerView)
            articleAdapter.clearList()
            viewModel.getNews()
            viewModel.getPersistentNews()
        }
    }

    /**
     * Used to set up the recycler view with articleadapter
     */
    private fun initEventRecyclerView(context: Context, recycler: RecyclerView) {
        recycler.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            if (recycler.itemDecorationCount < 1) {
                val spacingDecorator = VerticalDecorator(20)
                addItemDecoration(spacingDecorator)
            }
            articleAdapter =
                ArticleAdapter()
            adapter = articleAdapter
        }
    }

    private fun submitNews(articles: List<Article>) {
        articleAdapter.submitList(articles)
    }

}