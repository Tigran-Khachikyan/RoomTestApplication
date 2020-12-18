package com.example.roomtestapplication.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.Employee

@androidx.room.Database(
    entities = [Company::class, Employee::class],
    version = 1
)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getCompanyDao(): CompanyDao
    abstract fun getEmployeeDao(): EmployeeDao

    companion object {
        @Volatile
        private var instance: TaskDatabase? = null

        operator fun invoke(context: Context): TaskDatabase {
            return instance ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "TASK_DB"
                ).build()
                this.instance = instance
                return instance
            }
        }
    }
}