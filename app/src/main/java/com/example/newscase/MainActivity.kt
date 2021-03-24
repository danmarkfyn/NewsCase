package com.example.newscase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newscase.data.model.Article
import com.example.newscase.repositories.NewsRepository
import com.example.newscase.ui.adapters.ArticleAdapter
import com.example.newscase.ui.decorators.VerticalDecorator
import com.example.newscase.viewmodels.MainViewModel
import timber.log.Timber

/**
 * Main Activity of the Application
 * Used to fetch and display a list of news articles
 */
class MainActivity : AppCompatActivity() {

    // Repository and ViewModel
    private lateinit var repository: NewsRepository
    private lateinit var viewModel: MainViewModel

    // Adapters
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Timber Logger
        Timber.plant(Timber.DebugTree())

        // Initialize Repository and ViewModel
        repository = NewsRepository(this)
        viewModel = MainViewModel(repository)

        // UI elements
        val updateButton: Button = findViewById(R.id.update_button)
        val articleRecyclerView: RecyclerView = findViewById(R.id.article_recycleview)
        val loadIcon: ProgressBar = findViewById(R.id.news_screen_loading)
        val searchView: SearchView = findViewById(R.id.article_search_view)

        // Load icon for visual feedback on data fetching
        loadIcon.isVisible = true

        // Initializing RecyclerView
        initEventRecyclerView(this, articleRecyclerView)

        // Observe LiveData
        viewModel.news.observe(this, { response ->
            if (response?.articles != null) {
                loadIcon.isVisible = false // Hide buffer icon
                articleRecyclerView.isVisible = true
                submitNews(response.articles)
            }
        })

        // Update data listener
        updateButton.setOnClickListener {
            loadIcon.isVisible = true // visual feedback that the application tries to fetch data

            articleRecyclerView.isVisible = false
            searchView.setQuery("", false); // clears the searchbar
            searchView.clearFocus();
            viewModel.getNews()
            viewModel.getPersistentNews()
        }

        // filtering recyclerview items based on searchview input
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(keyword: String?): Boolean {
                articleAdapter.filter.filter(keyword)
                return false
            }
        })
    }

    /**
     * Used to set up the recycler view with an ArticleAdapter
     */
    private fun initEventRecyclerView(context: Context, recycler: RecyclerView) {
        recycler.apply {
            layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            val spacingDecorator = VerticalDecorator(28)
            addItemDecoration(spacingDecorator)
            articleAdapter =
                ArticleAdapter()
            adapter = articleAdapter
        }
    }

    private fun submitNews(articles: List<Article>) {
        articleAdapter.submitList(articles)
    }
}