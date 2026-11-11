package com.poshandrishti.app

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.ImageViewCompat
import com.poshandrishti.app.activities.HomeActivity

class MainActivity : AppCompatActivity() {

    // ROLE CARDS

    private lateinit var roleAnganwadi: LinearLayout
    private lateinit var roleHealthcare: LinearLayout
    private lateinit var roleAdmin: LinearLayout

    // ILLUSTRATIONS

    private lateinit var ivHealthcareIllustration: ImageView
    private lateinit var ivAdminIllustration: ImageView

    // INPUT FIELDS

    private lateinit var etMobile: EditText
    private lateinit var etPassword: EditText

    // VARIABLES

    private var selectedRole = "Anganwadi Worker"
    private var passwordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        // SYSTEM BARS

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { view, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // CONNECT ROLE CARDS

        roleAnganwadi = findViewById(R.id.roleAnganwadi)
        roleHealthcare = findViewById(R.id.roleHealthcare)
        roleAdmin = findViewById(R.id.roleAdmin)

        // CONNECT ILLUSTRATIONS

        ivHealthcareIllustration =
            findViewById(R.id.ivHealthcareIllustration)

        ivAdminIllustration =
            findViewById(R.id.ivAdminIllustration)

        // CONNECT INPUTS

        etMobile = findViewById(R.id.etMobile)
        etPassword = findViewById(R.id.etPassword)

        // INITIALIZE

        updateRoleStyles()
        setupRoleSelection()
        setupPasswordToggle()

        // FORGOT PASSWORD

        findViewById<TextView>(R.id.tvForgotPassword)
            .setOnClickListener {

                Toast.makeText(
                    this,
                    "Forgot Password clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // LOGIN BUTTON

        findViewById<Button>(R.id.btnLogin)
            .setOnClickListener {
                loginUser()
            }

        // OTP BUTTON

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

    // UPDATE ROLE STYLES

    private fun updateRoleStyles() {

        updateSingleRole(
            roleAnganwadi,
            selectedRole == "Anganwadi Worker",
            "Anganwadi\nWorker"
        )

        updateSingleRole(
            roleHealthcare,
            selectedRole == "Healthcare Worker",
            "Healthcare\nWorker"
        )

        updateSingleRole(
            roleAdmin,
            selectedRole == "Block / Admin Officer",
            "Block / Admin\nOfficer"
        )

        // SHOW HEALTHCARE ILLUSTRATION

        ivHealthcareIllustration.visibility =
            if (selectedRole == "Healthcare Worker") {
                View.VISIBLE
            } else {
                View.GONE
            }

        // SHOW ADMIN ILLUSTRATION

        ivAdminIllustration.visibility =
            if (selectedRole == "Block / Admin Officer") {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    // UPDATE INDIVIDUAL ROLE CARD

    private fun updateSingleRole(
        card: LinearLayout,
        isSelected: Boolean,
        label: String
    ) {

        val greenColor = Color.parseColor("#075B37")

        val textColor = if (isSelected) {
            Color.WHITE
        } else {
            greenColor
        }

        // BACKGROUND

        card.setBackgroundResource(
            if (isSelected) {
                R.drawable.bg_role_selected
            } else {
                R.drawable.bg_role_unselected
            }
        )

        // ICON

        val icon = card.getChildAt(0) as ImageView

        ImageViewCompat.setImageTintList(
            icon,
            ColorStateList.valueOf(textColor)
        )

        // ROLE TEXT

        val roleText = card.getChildAt(1) as TextView

        roleText.text = if (isSelected) {
            "$label ✓"
        } else {
            label
        }

        roleText.setTextColor(textColor)
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

                Toast.makeText(
                    this,
                    "Login successful!",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(
                    this,
                    HomeActivity::class.java
                )

                startActivity(intent)
                finish()
            }
        }
    }
}
