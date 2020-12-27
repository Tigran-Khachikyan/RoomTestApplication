package com.example.roomtestapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.*
import androidx.room.PrimaryKey

@Entity(
    tableName = "EMPLOYEE",
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["COM_ID"],
            childColumns = ["EMP_COM_ID"],
            onDelete = CASCADE,
            onUpdate = CASCADE,
            deferred = false
        ),
        ForeignKey(
            entity = Position::class,
            parentColumns = ["POS_ID"],
            childColumns = ["EMP_POS_ID"],
            onDelete = SET_DEFAULT,
            onUpdate = CASCADE,
            deferred = false
        )
    ]
)
data class Employee(

    @ColumnInfo(name = "FULL_NAME")
    val name: String,

    @ColumnInfo(name = "EXPERIENCE")
    val experience: Float,

    @ColumnInfo(name = "SALARY")
    val salary: Int,

    @ColumnInfo(name = "REMOTE")
    val remote: Boolean,

    @ColumnInfo(name = "EMP_POS_ID", defaultValue = "1")
    val pId: Int,

    @ColumnInfo(name = "EMP_COM_ID")
    val cId: Int

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "EMP_ID")
    var id: Int = 0
}

