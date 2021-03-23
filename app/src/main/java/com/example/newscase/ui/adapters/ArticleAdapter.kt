package com.example.newscase.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
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
class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(), Filterable {

    private var filteredList: MutableList<Article> = ArrayList() // list of articles according to filtering (full list per default)
    private var trueList: MutableList<Article> = ArrayList() // list of all articles unfiltered

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // UI elements of item view
        private val articleTitle: TextView = view.findViewById(R.id.article_title)
        private val articlePublishedDate: TextView = view.findViewById(R.id.article_published_value)
        private val articleImage: ImageView = view.findViewById(R.id.article_img)

        /**
         * Used to bind an article's fields to a item view
         */
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val keyword = constraint.toString().toLowerCase()
                val results = FilterResults()
                var sortedList: MutableList<Article> = ArrayList()

                // if a keyword exist, the list will be filtered according to article description
                if (keyword.trim().isNotEmpty()) {
                    for (article in trueList) {
                        if (article.title?.trim()?.toLowerCase()?.contains(keyword) == true) {
                            sortedList.add(article)
                        }
                    }
                } else {
                    sortedList = trueList
                }
                results.values = sortedList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Article>
                notifyDataSetChanged() // update adapter
            }
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        when (holder) {
            is ArticleViewHolder -> {
                val article = filteredList[position]
                holder.bind(article, holder)

                // Clicklistener for opening detailed view of selected article
                holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, ArticleActivity::class.java)
                    intent.putExtra("title", article.title)
                    intent.putExtra("author", article.author)
                    intent.putExtra("description", article.description)
                    if (article.source != null) {
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
        return filteredList.size
    }

    //Submits list of articles
    fun submitList(articleList: List<Article>) {
        filteredList = articleList as MutableList<Article>
        trueList = articleList
        notifyDataSetChanged()
    }
}