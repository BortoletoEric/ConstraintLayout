package com.example.constraintlayout

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams

class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private var currentAngle = 0f

    // Esse Runnable será executado a cada segundo
    private val updateAngleRunnable = object : Runnable {
        override fun run() {
            // Increment the angle. Use a value that looks good, like 6 degrees per update.
            // 360 degrees / 6 = 60 steps for a full circle.
            currentAngle = (currentAngle + 1) % 360

            // Update the layout parameters of the spinning TextView
            val textViewSpinner = findViewById<TextView>(R.id.textViewSpinner)
            textViewSpinner.updateLayoutParams<ConstraintLayout.LayoutParams> {
                circleAngle = currentAngle
            }

            // Define a rotação do próprio TextView para corresponder ao ângulo
            textViewSpinner.rotation = currentAngle

            // Schedule this same runnable to run again after 1 second (1000 milliseconds)
            handler.postDelayed(this, 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa o Handler
        handler = Handler(Looper.getMainLooper())
    }

    override fun onResume() {
        super.onResume()
        // Inicia a animação quando a activity se torna visível
        handler.post(updateAngleRunnable)
    }

    override fun onPause() {
        super.onPause()
        // Para a animação quando a activity não está mais visível (otimiza bateria)
        handler.removeCallbacks(updateAngleRunnable)
    }
}