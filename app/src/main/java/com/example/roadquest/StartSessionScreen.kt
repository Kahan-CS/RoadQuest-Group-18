package com.example.roadquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartSessionScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_session_screen)

        val confirmStartBttn: Button = findViewById(R.id.confirmStartBttn)
        confirmStartBttn.setOnClickListener {
            val intent = Intent(this, SessionMainUIScreen::class.java)
            startActivity(intent)
        }

        val returnBttn: Button = findViewById(R.id.returnBttn)
        returnBttn.setOnClickListener {
            val intent = Intent(this, LandingScreen::class.java)
            startActivity(intent)
        }
    }
}
