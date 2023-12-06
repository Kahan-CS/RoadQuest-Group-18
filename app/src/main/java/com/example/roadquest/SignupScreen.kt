package com.example.roadquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class SignupScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_screen)

        // Initially, variables for username and password have no value.
        // Value is assigned when user clicks the sign-in button.
        var username: String? = null
        var password: String? = null

        val editTextUsername: EditText = findViewById(R.id.editTextUsername) as EditText
        val editTextPassword: EditText = findViewById(R.id.editTextPassword) as EditText

        // Return to sign in screen.
        val signinButton: Button = findViewById(R.id.signinButton)
        signinButton.setOnClickListener {
            val intent = Intent(this, SigninScreen::class.java)
            startActivity(intent)
        }

        // Return to sign in screen.
        val signupButton: Button = findViewById(R.id.signupButton)
        signinButton.setOnClickListener {

            username = editTextUsername.text.toString()
            password = editTextPassword.text.toString()

            // Check if username and password are not empty before making the request
            if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
                // Handle empty fields as needed
                return@setOnClickListener
            }

            // This variable is in charge of creating the JSON data.
            val jsonData = """
                {
                    "username": "$username",
                    "password": "$password"
                }
            """.trimIndent()

            // Specify the media type and create the request body
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = jsonData.toRequestBody(mediaType)

            // Replace the URL with your actual server endpoint
            val url = "http://localhost:3000/create-account"

            // Create the OkHttp request
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            // Create the OkHttp client
            val client = OkHttpClient()

            // Execute the request asynchronously
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                    // Handle the server response here
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        // Process the response as needed
                    } else {
                        // Handle unsuccessful response
                    }
                }

                override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                    // Handle network failure
                }
            })


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
