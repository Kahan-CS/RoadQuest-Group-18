package com.example.roadquest

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.MapView

class SessionMainUIScreen : AppCompatActivity(), SensorEventListener {

    private lateinit var mapView: MapView
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastTime: Long = 0
    private var speed: Float = 0f
    private var speedList: MutableList<Float> = mutableListOf()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var speedDataTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_main_uiscreen)

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        speedDataTextView = findViewById(R.id.speedData) // replace with your actual TextView ID

        // Button takes you back to the main screen after a session has been terminated:
        val endSessionBttn: Button = findViewById(R.id.endSessionBttn)
        endSessionBttn.setOnClickListener {
            stopCapturingSpeeds()
            val finalAverageSpeed = calculateFinalAverageSpeed(speedList)
            // This sends the final average speed to the view sessions activity.
            intent.putExtra("Average Speed", finalAverageSpeed)
            openDialog()
        }

        // Start capturing speeds every five seconds
        startCapturingSpeeds()
    }

    // These functions focus on gathering speed and sensor information.
    private fun startCapturingSpeeds() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                averageSpeed()
                handler.postDelayed(this, 5000) // Capture speed every 5 seconds
            }
        }, 5000)
    }

    private fun stopCapturingSpeeds() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun averageSpeed() {
        if (speedList.isNotEmpty()) {
            // Calculate average speed within the last 5 seconds
            val averageSpeed = speedList.average()

            // Update the TextView with the average speed
            speedDataTextView.text = String.format("%.0f", averageSpeed) + " m/s"

            // Clear the speedList for the next interval
            speedList.clear()
        } else {
            // No data in the last 5 seconds
            speedDataTextView.text = "0.00 m/s"
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
        sensorManager.unregisterListener(this)
    }

    // Implement the SensorEventListener interface methods
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == accelerometer) {
            val currentTime = System.currentTimeMillis()
            val timeDifference = currentTime - lastTime

            if (timeDifference > 0) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                // Use a sensitivity factor to control the influence of acceleration
                val sensitivity = 0.05f

                // Calculate the magnitude of acceleration
                val acceleration = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()

                // Introduce a threshold below which acceleration is considered negligible
                val threshold = 0.05f

                if (acceleration > threshold) {
                    // Adjust the sensitivity
                    val adjustedAcceleration = acceleration * sensitivity

                    // Store the adjusted acceleration in the speedList
                    speedList.add(adjustedAcceleration)
                }

                lastTime = currentTime
            }
        }
    }

    private fun calculateFinalAverageSpeed(speedList: List<Float>): Float {
        var sum = 0f
        for (speed in speedList) {
            sum += speed
        }

        return if (speedList.isNotEmpty()) {
            sum / speedList.size
        } else {
            0f
        }
    }

    // This function operates the dialog for when a session is terminated.
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

