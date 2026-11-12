package com.poshandrishti.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.poshandrishti.app.R
class AssessmentFragment : Fragment(R.layout.fragment_assessment) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.cardRecordMeasurement)
            .setOnClickListener {

                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.pageContainer,
                        RecordMeasurementFragment()
                    )
                    .addToBackStack(null)
                    .commit()
            }

        view.findViewById<View>(R.id.cardNutritionAssessment)
            .setOnClickListener {
                showMessage("Nutritional Assessment")
            }

        view.findViewById<View>(R.id.cardGrowthTracking)
            .setOnClickListener {
                showMessage("Growth Tracking")
            }

        view.findViewById<View>(R.id.cardAssessmentHistory)
            .setOnClickListener {
                showMessage("Assessment History")
            }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
