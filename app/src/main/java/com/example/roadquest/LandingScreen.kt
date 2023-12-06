package com.example.roadquest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.content.PermissionChecker.checkSelfPermission
import android.app.Dialog

class LandingScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_screen)

        // Debug - signs you out. Either delete or move to another screen after testing.
        val signoutButton: Button = findViewById(R.id.signoutButtonTemp)
        signoutButton.setOnClickListener {
            val intent = Intent(this, SigninScreen::class.java)
            startActivity(intent)
        }

        // Button takes you to a new screen to start a session:
        val startSessionButton: Button = findViewById(R.id.startSession)
        startSessionButton.setOnClickListener {
            val locationMessage: String = "RideQuest needs your permission to access your device location. It is imperative that this permission is granted for the proper functioning of the software. Failure to comply with the request will disable access to system features."
            openDialog(locationMessage)
        }

        // Button takes you to a new screen to view sessions:
        val viewSessionsBttn: Button = findViewById(R.id.viewSessions)
        viewSessionsBttn.setOnClickListener {
            val intent = Intent(this, ViewSessionsScreen::class.java)
            startActivity(intent)
        }

        // Button takes you to settings:
        val settingsButton: Button = findViewById(R.id.settingsButton)
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsScreen::class.java)
            startActivity(intent)
        }

    }

    private fun openDialog(locationMessage: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.location_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val locationMessageTextView: TextView = dialog.findViewById(R.id.dialogLocationPermDesc)
        val allowBttn: Button = dialog.findViewById(R.id.allowBttn)
        val denyBttn: Button = dialog.findViewById(R.id.denyBttn)

        // Check if locationMessage is not null before using it
        if (locationMessage != null) {
            locationMessageTextView.text = locationMessage
        }

        allowBttn.setOnClickListener {
            dialog.dismiss()
            // Check if the permission is granted
            if (checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PERMISSION_GRANTED
            ) {
                // Permission is already granted
                startLocationActivity()
            } else {
                // Request location permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    YOUR_PERMISSION_REQUEST_CODE
                )
            }
        }

        denyBttn.setOnClickListener {
            // App closes if location is denied.
            finishAffinity()
        }
        dialog.show()
    }

    companion object {
        const val YOUR_PERMISSION_REQUEST_CODE = 123 // You can use any unique code
    }

    private fun startLocationActivity() {
        val intent = Intent(this, StartSessionScreen::class.java)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == YOUR_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with your logic
                startLocationActivity()
            } else {
                // Permission denied, handle accordingly
                // You may want to show a message to the user or take alternative action
                finishAffinity()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}
