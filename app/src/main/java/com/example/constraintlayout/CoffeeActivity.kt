package com.example.constraintlayout

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class CoffeeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.coffee)) { v, insets ->
            // Get the insets for the system bars (status bar, navigation bar)
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Apply padding to the root view to prevent content from going under the navigation bar
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)

            // *** THIS IS THE KEY PART ***
            // Find the guideline and update its position to match the status bar's height
            val guidelineTop = findViewById<androidx.constraintlayout.widget.Guideline>(R.id.guideline_top)
            guidelineTop.setGuidelineBegin(systemBars.top)

            // The top padding is now handled by our fake status bar, so we pass 0 for top padding.
            // Return the insets so the system can continue processing them.
            insets
        }
        val buttonBack: ImageView = findViewById(R.id.imageview_back)
        buttonBack.setOnClickListener {
            finish()
        }
    }
}