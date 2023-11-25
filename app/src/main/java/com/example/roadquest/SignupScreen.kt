package com.example.roadquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignupScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_screen)

        // Return to sign in screen.
        val signinButton: Button = findViewById(R.id.signinButton)
        signinButton.setOnClickListener {
            val intent = Intent(this, SigninScreen::class.java)
            startActivity(intent)
        }

        // Debug - takes you to landing screen, delete after testing.
        val debugButton: Button = findViewById(R.id.debugButtonTemp)
        debugButton.setOnClickListener {
            val intent = Intent(this, LandingScreen::class.java)
            startActivity(intent)
        }
    }
}
