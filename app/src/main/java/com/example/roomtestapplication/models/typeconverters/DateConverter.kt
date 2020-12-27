package com.example.roomtestapplication.models.typeconverters

import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DateConverter {

    private var df: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)  // "Jan 12, 1952"

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return value?.let {
            try {
                df.parse(it)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
        }
    }

    @TypeConverter
    fun fromDate(date: Date?): String? {
        return date?.let { df.format(it) }
    }
}