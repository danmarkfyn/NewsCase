package com.example.newscase.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newscase.R
import com.example.newscase.data.model.Article
import timber.log.Timber

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var articles: MutableList<Article> = ArrayList()

    class ArticleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val articleTitle: TextView = view.findViewById(R.id.article_title)
        private val articlePublishedDate: TextView = view.findViewById(R.id.article_published_value)

        fun bind(article: Article) {
            articleTitle.text = article.title
            articlePublishedDate.text = article.publishedAt

            view.setOnClickListener {

                Timber.d("Clicked on ${article.description}")


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
                holder.bind(article)

                holder.itemView.setOnClickListener {
                    /*
                    val intent = Intent(holder.itemView.context, ArticleActivity::class.java)
                    intent.putExtra("title", article.title)
                    intent.putExtra("description", article.description)
                    intent.putExtra("darkstatusbar", false)
                    ContextCompat.startActivity(holder.itemView.context, intent, null)

                     */
                }

                /*
                val fragment = ArticleFragment.newInstance()
                val transaction =
                    (holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.article_fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            */
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