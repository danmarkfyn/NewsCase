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

    @Query("SELECT * FROM news WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray): List<News>

    @Insert
    fun insertAll(vararg news: News)

    @Delete
    fun delete(news: News)
}