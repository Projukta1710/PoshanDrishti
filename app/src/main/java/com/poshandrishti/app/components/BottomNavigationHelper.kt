
package com.poshandrishti.app.components

import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.poshandrishti.app.R
import android.widget.LinearLayout

class BottomNavigationHelper(

    private val navigationView: View,
    private val onItemSelected: (Destination) -> Unit

) {

    // Five available navigation destinations
    enum class Destination(val label: String) {
        HOME("Home"),
        CHILDREN("Children"),
        ASSESS("Assess"),
        REPORTS("Reports"),
        PROFILE("Profile")
    }

    // Connect each tab with its XML ID and icon
    private data class NavigationItem(
        val destination: Destination,
        val rootId: Int,
        val iconId: Int
    )

    private val items = listOf(

        NavigationItem(
            Destination.HOME,
            R.id.navHome,
            R.drawable.ic_nav_home
        ),

        NavigationItem(
            Destination.CHILDREN,
            R.id.navChildren,
            R.drawable.ic_nav_children
        ),

        NavigationItem(
            Destination.ASSESS,
            R.id.navAssess,
            R.drawable.ic_nav_assess
        ),

        NavigationItem(
            Destination.REPORTS,
            R.id.navReports,
            R.drawable.ic_nav_reports
        ),

        NavigationItem(
            Destination.PROFILE,
            R.id.navProfile,
            R.drawable.ic_nav_profile
        )
    )

    // Home is selected by default
    private var selectedDestination = Destination.HOME

    init {

        // Add click listeners to all navigation items
        items.forEach { item ->

            val itemView =
                navigationView.findViewById<View>(item.rootId)

            itemView.setOnClickListener {

                select(item.destination)

                onItemSelected(item.destination)
            }
        }

        updateSelectedAppearance()
    }

    // Select a navigation tab
    fun select(destination: Destination) {

        selectedDestination = destination

        updateSelectedAppearance()
    }

    // Get currently selected tab
    fun getSelectedDestination(): Destination {

        return selectedDestination
    }

    // Update icons, labels and selected backgrounds
    private fun updateSelectedAppearance() {

        items.forEach { item ->

            val itemView =
                navigationView.findViewById<LinearLayout>(item.rootId)
            val content = itemView

            val icon = itemView.getChildAt(0) as ImageView

            val label = itemView.getChildAt(1) as TextView

            val isSelected =
                item.destination == selectedDestination

            // Apply selected background
            content.background = if (isSelected) {

                ContextCompat.getDrawable(
                    navigationView.context,
                    R.drawable.bg_nav_item_selected
                )

            } else {

                null
            }

            // Set correct icon
            icon.setImageResource(item.iconId)

            // Set correct label
            label.text = item.destination.label

            // Change label color
            label.setTextColor(
                ContextCompat.getColor(
                    navigationView.context,

                    if (isSelected)
                        R.color.poshan_green
                    else
                        R.color.poshan_text
                )
            )

            // Make selected label bold
            label.setTypeface(
                null,

                if (isSelected)
                    Typeface.BOLD
                else
                    Typeface.NORMAL
            )
        }
    }
}
