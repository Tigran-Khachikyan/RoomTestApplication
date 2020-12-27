package com.example.roomtestapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.models.DepartmentDetails

@Dao
interface DepartmentDao {


    @Query("SELECT * FROM DEPARTMENT")
    fun getObservable(): LiveData<List<Department>?>

    @Query("SELECT * FROM DEPARTMENT")
    suspend fun getAll(): List<Department>?

    @Transaction
    @Query("SELECT * FROM DEPARTMENT WHERE DEP_ID =:id")
    fun getDetails(id: Int): DepartmentDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(dep: Department)

    @Update
    suspend fun update(dep: Department)

    @Query("DELETE FROM DEPARTMENT WHERE DEP_ID =:id")
    suspend fun remove(id: Int)

    @Delete
    suspend fun remove(dep: Department)

    @Query("DELETE FROM DEPARTMENT")
    suspend fun removeAll()
}