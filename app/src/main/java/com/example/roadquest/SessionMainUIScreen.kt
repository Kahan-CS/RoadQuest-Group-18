package com.example.roadquest

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.MapView

class SessionMainUIScreen : AppCompatActivity() {

    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_main_uiscreen)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        // Button takes you to take you back to the main screen after a session has been terminated:
        val endSessionBttn: Button = findViewById(R.id.endSessionBttn)
        endSessionBttn.setOnClickListener {
            openDialog()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.end_session_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val endSessionMessageTextView: TextView = dialog.findViewById(R.id.finalizedSessionDesc)
        val confirmButton: Button = dialog.findViewById(R.id.confirmBttn)

        confirmButton.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, LandingScreen::class.java)
            startActivity(intent)
        }

        dialog.show()

    }

}