package com.example.thread.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtils {
    companion object {
        fun formatTimestampToMDYY(timestamp: Long): String {
            val formatter = SimpleDateFormat("M/d/yy", Locale.US)
            return formatter.format(Date(timestamp))
        }

        fun formatPostTimestamp(timestamp: Long): String {
            val date = Date()
            val currentTimestamp = date.time
            val differenceMillis = currentTimestamp - timestamp

            val seconds = differenceMillis / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            return when {
                seconds < 60 -> "${seconds}s"
                minutes < 60 -> "${minutes}m"
                hours < 24 -> "${hours}h"
                days < 7 -> "${days}d"
                else -> {
                    formatTimestampToMDYY(timestamp)
                }
            }
        }
    }
}
