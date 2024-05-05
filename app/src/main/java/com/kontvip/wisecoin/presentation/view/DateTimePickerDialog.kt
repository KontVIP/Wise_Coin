package com.kontvip.wisecoin.presentation.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.Calendar

interface DateTimePickerDialog {

    fun showDateTimePicker(context: Context, listener: DateTimePickListener)

    class Default : DateTimePickerDialog {
        override fun showDateTimePicker(context: Context, listener: DateTimePickListener) {
            val calendar = Calendar.getInstance()
            val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    val dateTimeMillis = calendar.timeInMillis
                    listener.onDateTimePicked(dateTimeMillis)
                }

                TimePickerDialog(
                    context,
                    timeListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }

            DatePickerDialog(
                context,
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    interface DateTimePickListener {
        fun onDateTimePicked(dateTimeMillis: Long)
    }

}