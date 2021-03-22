package com.example.newscase.utils

import java.util.*

/**
 * Used for extensions to objects
 */

/**
 * Get day and date from a Date object as formatted string.
 */
fun Date.getFormattedDate(): String {
    val calendar = Calendar.getInstance().apply {
        time = this@getFormattedDate
    }
    val month = calendar.get(Calendar.MONTH) + 1
    val year = calendar.get(Calendar.YEAR)
    return "${calendar.get(Calendar.DAY_OF_MONTH)}.$month.$year"
}