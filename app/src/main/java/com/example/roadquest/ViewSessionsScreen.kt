package com.example.roadquest

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class ViewSessionsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_sessions_screen)


        // Button returns you back to main interface.
        val returnBttn: Button = findViewById(R.id.returnBttn)
        returnBttn.setOnClickListener {
            val intent = Intent(this, LandingScreen::class.java)
            startActivity(intent)
        }

    }
}