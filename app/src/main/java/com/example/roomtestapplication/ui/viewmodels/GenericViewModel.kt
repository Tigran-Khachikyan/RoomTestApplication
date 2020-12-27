package com.example.roomtestapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.roomtestapplication.repositories.Repository

class GenericViewModel<T, K>(repository: Repository<T, K>) : ViewModel() {

    private val trigger = MutableLiveData<Unit>()

    init {
        refresh()
    }

    val data: LiveData<List<K>?> = trigger.switchMap {
        Log.d("asdhsd884d", "viewmodel is triggered")
        repository.getObservable()
    }

    fun refresh() {
        trigger.value = Unit
    }
}