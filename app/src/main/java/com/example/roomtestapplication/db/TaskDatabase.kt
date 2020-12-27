package com.example.roomtestapplication.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.Position
import com.example.roomtestapplication.models.typeconverters.DateConverter
import com.example.roomtestapplication.repositories.Repository
import com.example.roomtestapplication.ui.models.CompanyDepartmentJunction


@androidx.room.Database(
    entities = [Company::class, Department::class, Employee::class, Position::class, CompanyDepartmentJunction::class],
    version = 2
)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun getCompanyDao(): CompanyDao
    abstract fun getEmployeeDao(): EmployeeDao
    abstract fun getDepartmentDao(): DepartmentDao
    abstract fun getPositionDao(): PositionDao

    companion object {

/*        val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new table
                database.execSQL("CREATE TABLE DEPARTMENT (NAME TEXT, ID INTEGER PRIMARY KEY)")
                // Copy the data
                database.execSQL("INSERT INTO users_new (userid, username, last_update) SELECT userid, username, last_update FROM users")
                // Remove the old table
                database.execSQL("DROP TABLE users")
                // Change the table name to the correct one
                database.execSQL("ALTER TABLE users_new RENAME TO users")
            }
        }*/

        @Volatile
        private var instance: TaskDatabase? = null

        operator fun invoke(context: Context): TaskDatabase {
            return instance ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "TASK_DB"
                )
                    //.addMigrations(migration_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                this.instance = instance
                return instance
            }
        }
    }
}