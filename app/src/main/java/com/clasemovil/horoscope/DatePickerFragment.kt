package com.clasemovil.horoscope

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(val listener: (day: Int, month: Int, year: Int) -> Unit):DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    // Listener del calendario
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year)
    }

    // Crea el dialogo del calendario
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)

        val picker = DatePickerDialog(activity as Context, this, year, month, day)

        // Fecha máxima, hasta el día de hoy
        picker.datePicker.maxDate = cal.timeInMillis

        cal.add(Calendar.YEAR, -100)

        // Fecha mínima, hasta 100 años antes de hoy
        picker.datePicker.minDate = cal.timeInMillis

        return picker
    }
}