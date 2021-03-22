package com.example.newscase.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newscase.R
import com.example.newscase.data.model.Article
import com.example.newscase.ui.activities.ArticleActivity
import com.example.newscase.utils.getFormattedDate
import com.squareup.picasso.Picasso
import timber.log.Timber

/**
 * Adapter for inflating RecyclerView of article_items with data from Article objects
 */
class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articles: MutableList<Article> = ArrayList()

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val articleTitle: TextView = view.findViewById(R.id.article_title)
        private val articlePublishedDate: TextView = view.findViewById(R.id.article_published_value)
        private val articleImage: ImageView = view.findViewById(R.id.article_img)

        fun bind(article: Article, holder: RecyclerView.ViewHolder) {
            articleTitle.text = article.title
            articlePublishedDate.text = article.publishedAt!!.getFormattedDate()
            try {
                Timber.d("Fetching Image from URL")
                Picasso.with(holder.itemView.context).load(article.urlToImage)
                    .into(articleImage)
            } catch (e: Exception) {
                Timber.w("Could not fetch image from URL ${e.localizedMessage}\")")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellFromRow = layoutInflater.inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(cellFromRow)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                val article = articles[position]
                holder.bind(article, holder)

                holder.itemView.setOnClickListener {

                    val intent = Intent(holder.itemView.context, ArticleActivity::class.java)
                    intent.putExtra("title", article.title)
                    intent.putExtra("author", article.author)
                    intent.putExtra("description", article.description)
                    if(article.source != null) {
                        intent.putExtra("source", article.source!!.name)
                        Timber.d("Source: ${article.source}")
                    }
                    intent.putExtra("url", article.url)
                    intent.putExtra("darkstatusbar", false)
                    ContextCompat.startActivity(holder.itemView.context, intent, null)
                }
            }
        }
    }

    //Numbers of articles
    override fun getItemCount(): Int {
        return articles.size
    }

    //Submits list of articles
    fun submitList(articleList: List<Article>) {
        articles = articleList as MutableList<Article>
    }

    //clears list of articles
    fun clearList() {
        articles.clear()
    }
}