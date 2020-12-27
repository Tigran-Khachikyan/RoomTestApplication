package com.example.roomtestapplication.repositories

import androidx.lifecycle.LiveData
import com.example.roomtestapplication.db.TaskDatabase


//T type of entity
//K type of model in ui
interface Repository<T, K> {

    val database: TaskDatabase

    fun getObservable(): LiveData<List<K>?>

    suspend fun getAll(): List<K>?

    suspend fun getDetails(id: Int): Any?

    suspend fun add(item: T)

    suspend fun update(item: T)

    suspend fun remove(id: Int)

    suspend fun remove(item: T)

    suspend fun removeAll()
}