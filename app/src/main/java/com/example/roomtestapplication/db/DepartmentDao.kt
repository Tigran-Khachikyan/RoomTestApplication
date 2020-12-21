package com.example.roomtestapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.models.DepartmentWithEmployees

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM DEPARTMENT")
    fun getAll(): LiveData<List<Department>?>

    @Transaction
    @Query("SELECT * FROM DEPARTMENT")
    fun getDepartmentsWithEmployees(): LiveData<List<DepartmentWithEmployees>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(dep: Department)

    @Update
    suspend fun update(dep: Department)

    @Query("DELETE FROM DEPARTMENT WHERE ID =:id")
    suspend fun remove(id: Int)

    @Delete
    suspend fun remove(dep: Department)

    @Query("DELETE FROM DEPARTMENT")
    suspend fun removeAll()
}