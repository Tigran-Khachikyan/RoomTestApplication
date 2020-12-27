package com.example.roomtestapplication.repositories

import androidx.lifecycle.LiveData
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Position
import com.example.roomtestapplication.ui.models.PositionDetails

class PositionRepository(val db: TaskDatabase) : Repository<Position, PositionDetails> {

    override val database: TaskDatabase
        get() = db

    override fun getObservable(): LiveData<List<PositionDetails>?> {
        return db.getPositionDao().getObservable()
    }

    override suspend fun getAll(): List<PositionDetails>? {
        return db.getPositionDao().getAll()
    }

    override suspend fun getDetails(id: Int): Any? {
        return db.getPositionDao().getDetails(id)
    }

    override suspend fun add(item: Position) {
        db.getPositionDao().add(item)
    }

    override suspend fun update(item: Position) {
        db.getPositionDao().update(item)
    }

    override suspend fun remove(id: Int) {
        db.getPositionDao().remove(id)
    }

    override suspend fun remove(item: Position) {
        db.getPositionDao().remove(item)
    }

    override suspend fun removeAll() {
        db.getPositionDao().removeAll()
    }
}