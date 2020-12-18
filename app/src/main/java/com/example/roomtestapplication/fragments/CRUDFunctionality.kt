package com.example.roomtestapplication.fragments

interface CRUDFunctionality<T> {
    fun add(item: T)
    fun update(item: T)
    fun remove(item: T)
    fun observeData()
}