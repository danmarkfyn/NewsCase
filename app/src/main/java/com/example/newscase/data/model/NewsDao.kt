package com.example.newscase.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Data Access Object for the News Entity
 * Used for storage in Room DB
 */
@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getNews(): News
    // fun getAll(): List<News>

    @Query("SELECT * FROM news WHERE newsID IN (:newsIds)")
    fun loadAllByIds(newsIds: IntArray): List<News>

    /*
        @Query("SELECT * FROM news WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1")
        fun findByName(first: String, last: String): News

     */
    @Insert
    fun insertAll(vararg news: News)

    @Delete
    fun delete(user: News)
}