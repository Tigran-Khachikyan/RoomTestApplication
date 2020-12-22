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
            parentColumns = ["ID"],
            childColumns = ["COMPANY_ID"],
            onDelete = CASCADE,
            onUpdate = CASCADE,
            deferred = false
        ),
        ForeignKey(
            entity = Department::class,
            parentColumns = ["ID"],
            childColumns = ["DEP_ID"],
            onDelete = SET_DEFAULT,
            onUpdate = CASCADE,
            deferred = false
        )
    ]
)
data class Employee(

    @ColumnInfo(name = "FULL_NAME")
    val fullName: String,
    @ColumnInfo(name = "COMPANY_ID")
    val cId: Int,
    @ColumnInfo(name = "DEP_ID", defaultValue = "1")
    val dId: Int
) : GenericType {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int = 0
}

// onDelete = ForeignKey.CASCADE,  +
// onDelete = ForeignKey.NO_ACTION, + rejecting  -  FOREIGN KEY constraint failed (code 787 SQLITE_CONSTRAINT_FOREIGNKEY[787])
// onDelete = ForeignKey.RESTRICT, + rejecting  - FOREIGN KEY constraint failed (code 1811 SQLITE_CONSTRAINT_TRIGGER[1811])
// onDelete = ForeignKey.SET_NULL, + worked with Int?
// onDelete = ForeignKey.SET_DEFAULT, worked with  @ColumnInfo(name = "DEP_ID", defaultValue = "1")

// onUpdate = ForeignKey.CASCADE,  +
// onUpdate = ForeignKey.NO_ACTION, -
// onUpdate = ForeignKey.RESTRICT,   -
// onUpdate = ForeignKey.SET_NULL,   -
// onUpdate = ForeignKey.SET_DEFAULT, -

//each changes - java.lang.IllegalStateException: Room cannot verify the data integrity.
// Looks like you've changed schema but forgot to update the version number. You can simply fix this by increasing the version number.


//Room will always throw an IllegalStateException if you modify the database schema but do not update the version number.
//1. increment the database version
//2. provide a Migration
