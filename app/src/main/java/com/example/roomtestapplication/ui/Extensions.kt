package com.example.roomtestapplication.ui

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.roomtestapplication.R

data class SpinnerHolder(
    val entityId: Int,
    val title: String
)

fun Spinner.initialize(
    context: Context,
    data: List<SpinnerHolder>?,
    startPos: Int,
    onItemSelected: ((Int) -> Unit)? = null  // position
) {
    this.adapter = ArrayAdapter(
        context,
        R.layout.support_simple_spinner_dropdown_item,
        data?.map { it.title } ?: mutableListOf()
    )
    this.setSelection(startPos)

    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            data?.run {
                onItemSelected?.invoke(position)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }
}