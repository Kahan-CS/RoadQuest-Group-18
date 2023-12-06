package com.example.roadquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_screen)


        // Button returns you back to main interface.
        val returnBttn: Button = findViewById(R.id.returnButton)
        returnBttn.setOnClickListener {
            val intent = Intent(this, LandingScreen::class.java)
            startActivity(intent)
        }

    }
}