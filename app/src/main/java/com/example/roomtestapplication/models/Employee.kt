package com.example.roomtestapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "EMPLOYEE",
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["ID"],
            childColumns = ["COMPANY_ID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = false
        )
    ]
)
data class Employee(

    @ColumnInfo(name = "FULL_NAME")
    val fullName: String,
    @ColumnInfo(name = "COMPANY_ID")
    val cId: Int
) : GenericType {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int = 0
}