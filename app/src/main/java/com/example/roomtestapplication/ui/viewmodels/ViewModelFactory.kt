package com.example.roomtestapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomtestapplication.repositories.Repository

class ViewModelFactory<T, K>(
    private val repository: Repository<T, K>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GenericViewModel(repository) as T
    }
}