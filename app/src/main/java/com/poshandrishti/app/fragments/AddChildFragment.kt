
package com.poshandrishti.app.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.poshandrishti.app.R
import java.util.Calendar

class AddChildFragment : Fragment(R.layout.fragment_add_child) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<EditText>(R.id.etChildName)
        val dob = view.findViewById<EditText>(R.id.etDateOfBirth)
        val gender = view.findViewById<Spinner>(R.id.spGender)
        val weight = view.findViewById<EditText>(R.id.etWeight)
        val height = view.findViewById<EditText>(R.id.etHeight)
        val muac = view.findViewById<EditText>(R.id.etMuac)
        val guardian = view.findViewById<EditText>(R.id.etGuardianName)
        val mobile = view.findViewById<EditText>(R.id.etGuardianMobile)
        val save = view.findViewById<Button>(R.id.btnSaveChild)
        val back = view.findViewById<TextView>(R.id.btnBackAddChild)


        // Gender dropdown
        val genders = listOf("Select Gender", "Male", "Female", "Other")

        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            genders
        ) {
            override fun getView(
                position: Int,
                convertView: android.view.View?,
                parent: android.view.ViewGroup
            ): android.view.View {
                val textView = super.getView(position, convertView, parent) as TextView
                textView.setTextColor(android.graphics.Color.parseColor("#8A8A8A"))
                textView.textSize = 14f
                return textView
            }

            override fun getDropDownView(
                position: Int,
                convertView: android.view.View?,
                parent: android.view.ViewGroup
            ): android.view.View {
                val textView = super.getDropDownView(position, convertView, parent) as TextView
                textView.setTextColor(android.graphics.Color.parseColor("#333333"))
                textView.textSize = 14f
                return textView
            }
        }

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        gender.adapter = adapter

        // Date picker
        dob.setOnClickListener {
            val calendar = Calendar.getInstance()

            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    dob.setText(
                        String.format(
                            java.util.Locale.getDefault(),
                            "%02d/%02d/%04d",
                            day,
                            month + 1,
                            year
                        )
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Back button
        back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Save child
        save.setOnClickListener {

            if (name.text.isBlank()) {
                name.error = "Enter child's name"
                name.requestFocus()
                return@setOnClickListener
            }

            if (dob.text.isBlank()) {
                dob.error = "Select date of birth"
                return@setOnClickListener
            }

            if (gender.selectedItemPosition == 0) {
                Toast.makeText(
                    requireContext(),
                    "Please select gender",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (weight.text.isBlank() || height.text.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Enter weight and height",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (guardian.text.isBlank()) {
                guardian.error = "Enter guardian name"
                return@setOnClickListener
            }

            val phone = mobile.text.toString()
            if (phone.isNotBlank() && !phone.matches(Regex("[0-9]{10}"))) {
                mobile.error = "Enter a valid 10-digit number"
                return@setOnClickListener
            }
            val child = ChildRecord(
                name = name.text.toString().trim(),
                dob = dob.text.toString().trim(),
                gender = gender.selectedItem.toString(),
                weight = weight.text.toString().trim(),
                height = height.text.toString().trim(),
                muac = muac.text.toString().trim(),
                guardian = guardian.text.toString().trim(),
                mobile = mobile.text.toString().trim()
            )

            ChildStorage.saveChild(requireContext(), child)

            Toast.makeText(
                requireContext(),
                "Child details entered successfully!",
                Toast.LENGTH_LONG
            ).show()

            parentFragmentManager.popBackStack()
        }
    }
}
