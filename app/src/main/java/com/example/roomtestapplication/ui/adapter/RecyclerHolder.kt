package com.example.roomtestapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerHolder<T>(
    parent: ViewGroup,
    res: Int
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(res, parent, false)) {

    abstract fun bind(model: T)
}