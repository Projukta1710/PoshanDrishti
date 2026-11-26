
package com.poshandrishti.app.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.poshandrishti.app.R
import com.poshandrishti.app.components.BottomNavigationHelper
import com.poshandrishti.app.fragments.PlaceholderFragment
import androidx.activity.OnBackPressedCallback
import com.poshandrishti.app.fragments.ChildrenFragment
import com.poshandrishti.app.fragments.AssessmentFragment
class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationHelper: BottomNavigationHelper

    private lateinit var homeContent: View
    private lateinit var pageContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawerLayout)
        homeContent = findViewById(R.id.homeContent)
        pageContainer = findViewById(R.id.pageContainer)

        // Hamburger menu
        findViewById<TextView>(R.id.btnMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Home drawer item
        findViewById<TextView>(R.id.menuHome).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            showHome()
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

        // Logout
        findViewById<TextView>(R.id.menuLogout).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(
                this,
                "Logout functionality coming soon",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Bottom navigation
        bottomNavigationHelper = BottomNavigationHelper(
            findViewById(R.id.bottomNavigationScroll)
        ) { destination ->

            when (destination) {
                BottomNavigationHelper.Destination.HOME -> showHome()

                BottomNavigationHelper.Destination.CHILDREN ->
                    showChildren()

                BottomNavigationHelper.Destination.ASSESS ->
                    showAssessment()

                BottomNavigationHelper.Destination.REPORTS ->
                    showPage("Supplies")

                BottomNavigationHelper.Destination.PROFILE ->
                    showPage("Profile")
            }
        }
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when {
                        drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                            drawerLayout.closeDrawer(GravityCompat.START)
                        }
                        supportFragmentManager.backStackEntryCount > 0 -> {
                            supportFragmentManager.popBackStack()
                        }
                        pageContainer.visibility == View.VISIBLE -> {
                            showHome()
                        }
                        else -> {
                            finish()
                        }
                    }
                }
            }
        )
    }

    private fun showHome() {
        homeContent.visibility = View.VISIBLE
        pageContainer.visibility = View.GONE
    }

    private fun showPage(title: String) {
        homeContent.visibility = View.GONE
        pageContainer.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.pageContainer,
                PlaceholderFragment.newInstance(title)
            )
            .commit()
    }
    private fun showChildren() {
        homeContent.visibility = View.GONE
        pageContainer.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.pageContainer, ChildrenFragment())
            .commit()
    }
    private fun showAssessment() {
        homeContent.visibility = View.GONE
        pageContainer.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.pageContainer, AssessmentFragment())
            .commit()
    }
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
}
