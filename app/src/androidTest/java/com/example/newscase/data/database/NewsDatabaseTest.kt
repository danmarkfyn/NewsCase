package com.example.newscase.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.newscase.data.model.Article
import com.example.newscase.data.model.News
import com.example.newscase.data.model.NewsDao
import com.example.newscase.data.model.Source
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

/**
 * Test class for Room database (NewsDatabase)
 * Tests are performed according to AAA unit testing
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class NewsDatabaseTest {

    private lateinit var newsDao: NewsDao
    private lateinit var database: NewsDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java).build()
        newsDao = database.newsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadNewsToDatabase() {

        // Arrange
        val source = Source("testId", "testName")
        val article = Article(
            1, "testTitle", "testImgURL",
            "testURL", Calendar.getInstance().time, "testName", "testAuthor",
            source, "testDescription"
        )
        val articleList = ArrayList<Article>()
        articleList.add(article)

        val news = News(1, 9, "ok", articleList)

        // Act
        newsDao.insertNews(news)

        val value = newsDao.getNews()

        // Assert
        assertThat(value, not(equalTo(null)))
        assertThat(value.id, equalTo(news.id))
    }

    @Test
    @Throws(Exception::class)
    fun deleteNewsFromDatabase() {

        // Arrange
        val source = Source("testId", "testName")
        val article = Article(
            1, "testTitle", "testImgURL",
            "testURL", Calendar.getInstance().time, "testName", "testAuthor",
            source, "testDescription"
        )
        val articleList = ArrayList<Article>()
        articleList.add(article)

        val news = News(1, 9, "ok", articleList)

        // Act
        newsDao.insertNews(news)
        newsDao.delete(news)

        val value = newsDao.getNews()

        // Assert
        assertThat(value, equalTo(null))
        assertThat(value, not(equalTo(news)))
    }
}