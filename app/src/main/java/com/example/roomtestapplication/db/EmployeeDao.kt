package com.example.roomtestapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.EmployeeDetails

@Dao
interface EmployeeDao {


    @Query("SELECT EMPLOYEE.*, POSITION.*, DEP_NAME, COM_NAME FROM EMPLOYEE INNER JOIN POSITION ON EMP_POS_ID = POS_ID INNER JOIN DEPARTMENT ON POS_DEP_ID = DEP_ID INNER JOIN COMPANY ON EMP_COM_ID = COM_ID ORDER BY COM_NAME")
    fun getObservable(): LiveData<List<EmployeeDetails>?>

    @Query("SELECT EMPLOYEE.*, POSITION.*, DEP_NAME, COM_NAME FROM EMPLOYEE INNER JOIN POSITION ON EMP_POS_ID = POS_ID INNER JOIN DEPARTMENT ON POS_DEP_ID = DEP_ID INNER JOIN COMPANY ON EMP_COM_ID = COM_ID ORDER BY COM_NAME")
    fun getAll(): List<EmployeeDetails>?

    @Query("SELECT EMPLOYEE.*, POSITION.*, DEP_NAME, COM_NAME FROM EMPLOYEE INNER JOIN POSITION ON EMP_POS_ID = POS_ID INNER JOIN DEPARTMENT ON POS_DEP_ID = DEP_ID INNER JOIN COMPANY ON EMP_COM_ID = COM_ID WHERE EMPLOYEE.EMP_ID =:id")
    suspend fun getDetails(id: Int): EmployeeDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(employee: Employee)

    @Update
    suspend fun update(employee: Employee)

    @Query("DELETE FROM EMPLOYEE WHERE EMP_ID =:id")
    suspend fun remove(id: Int)

    @Delete
    suspend fun remove(employee: Employee)

    @Query("DELETE FROM EMPLOYEE")
    suspend fun removeAll()
}