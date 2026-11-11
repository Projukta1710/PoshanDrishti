
package com.poshandrishti.app.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.poshandrishti.app.R

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize drawer
        drawerLayout = findViewById(R.id.drawerLayout)

        // Hamburger menu click
        findViewById<TextView>(R.id.btnMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Home menu
        findViewById<TextView>(R.id.menuHome).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Other drawer menu options
        setupMenuItem(R.id.menuCentre, "My Anganwadi Centre")
        setupMenuItem(R.id.menuBeneficiaries, "Beneficiary Registration")
        setupMenuItem(R.id.menuSchemes, "Nutrition Schemes")
        setupMenuItem(R.id.menuNotifications, "Notifications")
        setupMenuItem(R.id.menuSync, "Sync Data")
        setupMenuItem(R.id.menuHistory, "Activity History")
        setupMenuItem(R.id.menuHelp, "Help & Support")
        setupMenuItem(R.id.menuAbout, "About PoshanDrishti")

        // Logout placeholder
        findViewById<TextView>(R.id.menuLogout).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            Toast.makeText(
                this,
                "Logout functionality coming soon",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Handle drawer menu clicks
    private fun setupMenuItem(id: Int, name: String) {

        findViewById<TextView>(id).setOnClickListener {

            drawerLayout.closeDrawer(GravityCompat.START)

            Toast.makeText(
                this,
                "$name selected",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Handle Android back button
    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
