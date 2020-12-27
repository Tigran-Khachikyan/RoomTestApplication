package com.example.roomtestapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.*
import androidx.room.PrimaryKey

@Entity(
    tableName = "POSITION",
    foreignKeys = [
        ForeignKey(
            entity = Department::class,
            parentColumns = ["DEP_ID"],
            childColumns = ["POS_DEP_ID"],
            onDelete = CASCADE,
            onUpdate = CASCADE,
            deferred = false
        )
    ]
)
data class Position(

    @ColumnInfo(name = "TITLE")
    var title: String,

    @ColumnInfo(name = "REMOTE_SUPPORTED")
    var remote: Boolean,

    @ColumnInfo(name = "POS_DEP_ID")
    val dId: Int

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "POS_ID")
    var id: Int = 0
}

