
package com.poshandrishti.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.poshandrishti.app.R

class ChildProfileFragment : Fragment(R.layout.fragment_child_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val back = view.findViewById<TextView>(R.id.btnBackProfile)
        val overviewTab = view.findViewById<TextView>(R.id.tabOverview)
        val growthTab = view.findViewById<TextView>(R.id.tabGrowth)

        val overviewContent = view.findViewById<View>(R.id.overviewContent)
        val growthContent = view.findViewById<View>(R.id.growthContent)

        val addMeasurement =
            view.findViewById<Button>(R.id.btnAddMeasurement)

        // Back button
        back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Overview tab
        overviewTab.setOnClickListener {
            overviewContent.visibility = View.VISIBLE
            growthContent.visibility = View.GONE

            overviewTab.setBackgroundResource(R.drawable.bg_primary_button)
            overviewTab.setTextColor(
                android.graphics.Color.WHITE
            )

            growthTab.background = null
            growthTab.setTextColor(
                android.graphics.Color.BLACK
            )
        }

        // Growth and Status tab
        growthTab.setOnClickListener {
            overviewContent.visibility = View.GONE
            growthContent.visibility = View.VISIBLE

            growthTab.setBackgroundResource(R.drawable.bg_primary_button)
            growthTab.setTextColor(
                android.graphics.Color.WHITE
            )

            overviewTab.background = null
            overviewTab.setTextColor(
                android.graphics.Color.BLACK
            )
        }

        // Add Measurement button
        addMeasurement.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Add Measurement selected",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Edit profile button
        view.findViewById<TextView>(R.id.btnEditProfile)
            .setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Edit Profile selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
