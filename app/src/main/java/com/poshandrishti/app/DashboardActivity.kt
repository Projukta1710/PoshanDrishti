
package com.poshandrishti.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.poshandrishti.app.components.BottomNavigationHelper
import com.poshandrishti.app.fragments.PlaceholderFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load dashboard layout
        setContentView(R.layout.activity_dashboard)

        // Find navigation component
        val navigationView = findViewById<View>(
            R.id.bottomNavigation
        )

        // Initialize navigation helper
        bottomNavigation = BottomNavigationHelper(navigationView) { destination ->

            // Open selected screen
            loadScreen(destination)
        }

        // Show Home screen initially
        if (savedInstanceState == null) {

            bottomNavigation.select(
                BottomNavigationHelper.Destination.HOME
            )

            loadScreen(
                BottomNavigationHelper.Destination.HOME
            )
        }
    }

    // Load the selected Fragment
    private fun loadScreen(
        destination: BottomNavigationHelper.Destination
    ) {

        val fragment = PlaceholderFragment.newInstance(
            destination.label
        )

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.screenContainer, fragment)
            .commit()
    }
}
