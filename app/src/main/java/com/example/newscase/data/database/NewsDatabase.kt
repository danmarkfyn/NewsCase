package com.example.newscase.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newscase.data.model.News
import com.example.newscase.data.model.NewsDao

/**
 * Room database for News
 */
@Database(entities = [News::class], version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}