package com.example.newscase.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

/**
 * Model for news sources
 */
data class Source(
    @SerializedName("id")
    @ColumnInfo val id: String? = null,

    @SerializedName("name")
    @ColumnInfo(name = "name") val name: String? = null,
    ){
    override fun toString(): String {
        return "${id.toString()}"
    }
}