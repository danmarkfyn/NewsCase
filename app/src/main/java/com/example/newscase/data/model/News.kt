package com.example.newscase.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Data object for News
 */
@Entity
data class News(
    @SerializedName("newsID")
    @PrimaryKey val newsID: Int? = null,

    @SerializedName("totalResults")
    @ColumnInfo(name = "total_results") val totalResults: Int? = null,

    @SerializedName("status")
    @ColumnInfo(name = "status") val status: String? = null,

    @SerializedName("articles")
    @ColumnInfo(name = "articles") val articles: List<Article>? = null
) {
    override fun toString(): String {

        var output = ""
        if (articles != null) {
            for (article in articles) {
                output += article.toString()
            }
        }
        return "${totalResults.toString()} \n ${output} \n"
    }
}