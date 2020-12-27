package com.example.roomtestapplication.models

import androidx.room.*
import com.example.roomtestapplication.models.typeconverters.DateConverter
import java.util.*

@Entity(tableName = "COMPANY")
data class Company(

    @ColumnInfo(name = "COM_NAME")
    val name: String,

    @ColumnInfo(name = "DATE_OF_FOUNDATION")
    @TypeConverters(DateConverter::class)
    val dateFounded: Date

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "COM_ID")
    var id: Int = 0
}