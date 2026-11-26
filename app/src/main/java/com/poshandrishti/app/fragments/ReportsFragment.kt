
package com.poshandrishti.app.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.poshandrishti.app.R

class ReportsFragment : Fragment(R.layout.fragment_reports) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val month = view.findViewById<TextView>(R.id.filterMonth)
        val threeMonths = view.findViewById<TextView>(R.id.filterThreeMonths)
        val year = view.findViewById<TextView>(R.id.filterYear)

        val screened = view.findViewById<TextView>(R.id.valueScreened)
        val recovered = view.findViewById<TextView>(R.id.valueRecovered)
        val sam = view.findViewById<TextView>(R.id.valueSam)

        val nutritionChart =
            view.findViewById<NutritionBarChartView>(R.id.nutritionChart)

        val growthChart =
            view.findViewById<GrowthLineChartView>(R.id.growthChart)

        fun selectFilter(selected: TextView) {
            listOf(month, threeMonths, year).forEach {
                it.setBackgroundResource(R.drawable.report_filter)
                it.setTextColor(Color.BLACK)
            }

            selected.setBackgroundResource(R.drawable.report_filter_selected)
            selected.setTextColor(Color.WHITE)
        }

        month.setOnClickListener {
            selectFilter(month)
            screened.text = "44"
            recovered.text = "4"
            sam.text = "1"

            nutritionChart.setValues(listOf(36f, 9f, 1f))
            growthChart.setValues(
                listOf(1.2f, 0.8f, 0.4f, 0.0f, -0.4f, -0.8f)
            )
        }

        threeMonths.setOnClickListener {
            selectFilter(threeMonths)
            screened.text = "42"
            recovered.text = "6"
            sam.text = "2"

            nutritionChart.setValues(listOf(34f, 6f, 2f))
            growthChart.setValues(
                listOf(1.3f, 0.9f, 0.5f, 0.2f, -0.2f, -0.6f)
            )
        }

        year.setOnClickListener {
            selectFilter(year)
            screened.text = "48"
            recovered.text = "10"
            sam.text = "3"

            nutritionChart.setValues(listOf(37f, 10f, 3f))
            growthChart.setValues(
                listOf(1.5f, 1.1f, 0.7f, 0.3f, -0.1f, -0.5f)
            )
        }

        nutritionChart.setValues(listOf(36f, 9f, 1f))
        growthChart.setValues(
            listOf(1.2f, 0.8f, 0.4f, 0.0f, -0.4f, -0.8f)
        )

        view.findViewById<TextView>(R.id.btnExportPdf).setOnClickListener {
            Toast.makeText(
                requireContext(),
                "PDF export will be connected next.",
                Toast.LENGTH_SHORT
            ).show()
        }

        view.findViewById<TextView>(R.id.btnExportExcel).setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Excel export will be connected next.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
