package com.example.newscase.data.database

import androidx.room.TypeConverter
import com.example.newscase.data.model.Article
import com.example.newscase.data.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * TypeConverter for complex data that need to be stored
 * in a Room database
 */
class DatabaseConverters {

    // Used to convert List<Article> to Json that can be used by DB
    @TypeConverter
    fun listToJson(value: List<Article>?) = Gson().toJson(value)

    // Used to convert List<Article> to Json that can be used by DB
    @TypeConverter
    fun toJson(value: Source?): String{
        val type = object: TypeToken<Source>() {}.type
        return Gson().toJson(value, type)
    }
    //= Gson().toJson(value)

    // Used to convert Json to List<Article>
    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Article>::class.java).toList()

}