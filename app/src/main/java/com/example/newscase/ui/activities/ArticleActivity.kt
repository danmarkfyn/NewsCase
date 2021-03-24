package com.example.newscase.ui.activities

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import com.example.newscase.R

class ArticleActivity : AppCompatActivity() {

    private lateinit var articleTitle: String
    private lateinit var articleAuthor: String
    private lateinit var articleDescription: String
    private lateinit var articleSource: String
    private lateinit var articleURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_article)

        // Get data from intent
        val bundle = intent.extras
        articleTitle = bundle?.getString("title", "") ?: ""
        articleAuthor = bundle?.getString("author", "") ?: ""
        articleDescription = bundle?.getString("description", "") ?: ""
        articleSource = bundle?.getString("source", "") ?: ""
        articleURL = bundle?.getString("url", "") ?: ""

        // Get GUI elements
        val articleTitleText: TextView = findViewById(R.id.article_window_title)
        val articleAuthorText: TextView = findViewById(R.id.article_window_author)
        val articleDescriptionText: TextView =
            findViewById(R.id.article_window_description)
        val articleSourceText: TextView = findViewById(R.id.article_window_source)
        val articleUrlText: TextView = findViewById(R.id.article_window_url)
        val returnButton: Button = findViewById(R.id.return_button)

        // Sets article data
        articleTitleText.text = articleTitle
        articleAuthorText.text = articleAuthor
        articleDescriptionText.text = articleDescription
        articleUrlText.text = articleURL
        if (articleSource != null) {
            articleSourceText.text = articleSource
        }

        // Transition animation
        val activityView: ConstraintLayout = findViewById(R.id.article_window_background)
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 800 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            activityView.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

        // Exit activity
        returnButton.setOnClickListener {
            finish()
        }

        // Listener for opening link to original news post
        articleUrlText.setOnClickListener {
            val openURI = Intent(Intent.ACTION_VIEW)
            openURI.data = Uri.parse(articleURL)
            startActivity(openURI)
        }
    }
}