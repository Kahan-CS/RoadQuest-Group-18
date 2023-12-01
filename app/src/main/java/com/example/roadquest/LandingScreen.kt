package com.example.roadquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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

        // Button takes you to new screen to start a session:
        val startSessionButton: Button = findViewById(R.id.startSession)
        startSessionButton.setOnClickListener {
            val intent = Intent(this, StartSessionScreen::class.java)
            startActivity(intent)
        }


    }
}