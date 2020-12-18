package com.example.roomtestapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomtestapplication.models.Employee

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM EMPLOYEE")
     fun getAll(): LiveData<List<Employee>?>

    @Query("SELECT * FROM EMPLOYEE WHERE ID =:id")
     fun getById(id: Int): LiveData<Employee?>

    @Query("SELECT * FROM EMPLOYEE WHERE COMPANY_ID =:companyId")
    suspend fun getCompanyEmployees(companyId: Int): List<Employee>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(employee: Employee)

    @Update
    suspend fun update(employee: Employee)

    @Query("DELETE FROM EMPLOYEE WHERE ID =:id")
    suspend fun remove(id: Int)

    @Delete
    suspend fun remove(employee: Employee)

    @Query("DELETE FROM EMPLOYEE")
    suspend fun removeAll()
}