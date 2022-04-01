package com.example.studying

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDate(date:Date)=date.time
    @TypeConverter
    fun toDate(timeInMillis:Long)=Date(timeInMillis)
}