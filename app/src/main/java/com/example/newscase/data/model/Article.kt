package com.example.newscase.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Article(

    @SerializedName("newsID")
    @PrimaryKey val newsID: Int? = null,

    @SerializedName("title")
    @ColumnInfo(name = "title") val title: String? = null,


    @SerializedName("id")
    @ColumnInfo(name = "id") val id: String? = null,


    @SerializedName("name")
    @ColumnInfo(name = "name") val name: String? = null,


    @SerializedName("author")
    @ColumnInfo(name = "author") val author: String? = null,


    @SerializedName("description")
    @ColumnInfo(name = "description") val description: String? = null


) {
    override fun toString(): String {
        return "${title.toString()} \n Author: ${author.toString()} \n ${description.toString()} \n" + "\n"
    }
}