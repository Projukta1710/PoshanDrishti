
package com.poshandrishti.app.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.poshandrishti.app.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RecordMeasurementFragment :
    Fragment(R.layout.fragment_record_measurement) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateField = view.findViewById<EditText>(
            R.id.etMeasurementDate
        )

        dateField.setOnClickListener {
            val calendar = Calendar.getInstance()

            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    calendar.set(year, month, day)

                    val format = SimpleDateFormat(
                        "dd MMM yyyy",
                        Locale.getDefault()
                    )

                    dateField.setText(format.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}
