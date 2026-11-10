
package com.poshandrishti.app

import android.os.Bundle
import android.graphics.Color
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var roleAnganwadi: TextView
    private lateinit var roleHealthcare: TextView
    private lateinit var roleAdmin: TextView

    private lateinit var etMobile: EditText
    private lateinit var etPassword: EditText

    private var selectedRole = "Anganwadi Worker"
    private var passwordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        // Load login screen
        setContentView(R.layout.activity_main)

        // Handle system bars
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Connect XML elements
        roleAnganwadi = findViewById(R.id.roleAnganwadi)
        roleHealthcare = findViewById(R.id.roleHealthcare)
        roleAdmin = findViewById(R.id.roleAdmin)

        etMobile = findViewById(R.id.etMobile)
        etPassword = findViewById(R.id.etPassword)

        // Initialize functions
        setupRoleSelection()
        setupPasswordToggle()

        // Forgot Password
        findViewById<TextView>(R.id.tvForgotPassword)
            .setOnClickListener {

                Toast.makeText(
                    this,
                    "Forgot Password clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // Login Button
        findViewById<Button>(R.id.btnLogin)
            .setOnClickListener {
                loginUser()
            }

        // OTP Button
        findViewById<Button>(R.id.btnOtp)
            .setOnClickListener {

                val mobile = etMobile.text.toString().trim()

                if (mobile.length != 10) {

                    etMobile.error =
                        "Enter valid 10-digit mobile number"

                } else {

                    Toast.makeText(
                        this,
                        "OTP feature will be added next!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // ROLE SELECTION
    private fun setupRoleSelection() {

        roleAnganwadi.setOnClickListener {
            selectedRole = "Anganwadi Worker"
            updateRoleStyles()
        }

        roleHealthcare.setOnClickListener {
            selectedRole = "Healthcare Worker"
            updateRoleStyles()
        }

        roleAdmin.setOnClickListener {
            selectedRole = "Block / Admin Officer"
            updateRoleStyles()
        }
    }

    // UPDATE ROLE APPEARANCE
    private fun updateRoleStyles() {

        val roles = listOf(
            roleAnganwadi,
            roleHealthcare,
            roleAdmin
        )

        val labels = listOf(
            "♙\nAnganwadi\nWorker",
            "♡\nHealthcare\nWorker",
            "⌂\nBlock / Admin\nOfficer"
        )

        val selectedIndex = when (selectedRole) {
            "Anganwadi Worker" -> 0
            "Healthcare Worker" -> 1
            else -> 2
        }

        roles.forEachIndexed { index, role ->

            if (index == selectedIndex) {

                role.setBackgroundResource(
                    R.drawable.bg_role_selected
                )

                role.setTextColor(Color.WHITE)
                role.text = labels[index] + " ✓"

            } else {

                role.setBackgroundResource(
                    R.drawable.bg_role_unselected
                )

                role.setTextColor(
                    Color.parseColor("#075B37")
                )

                role.text = labels[index]
            }
        }
    }

    // SHOW / HIDE PASSWORD
    private fun setupPasswordToggle() {

        findViewById<TextView>(R.id.tvTogglePassword)
            .setOnClickListener {

                passwordVisible = !passwordVisible

                etPassword.transformationMethod =
                    if (passwordVisible) {

                        HideReturnsTransformationMethod.getInstance()

                    } else {

                        PasswordTransformationMethod.getInstance()
                    }

                etPassword.setSelection(
                    etPassword.text.length
                )
            }
    }

    // LOGIN VALIDATION
    private fun loginUser() {

        val mobile = etMobile.text.toString().trim()
        val password = etPassword.text.toString()

        when {

            mobile.length != 10 -> {

                etMobile.error =
                    "Enter a valid 10-digit number"
            }

            password.isBlank() -> {

                etPassword.error =
                    "Please enter your password"
            }

            else -> {

                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
