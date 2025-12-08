package com.example.hd2wartracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val totalMedalsText = findViewById<TextView>(R.id.totalMedals)
        val progressBar = findViewById<ProgressBar>(R.id.mainProgressBar)
        val btnSwitch = findViewById<ImageButton>(R.id.moc_button)
        val btnVc = findViewById<ImageButton>(R.id.vc_button)

        totalMedalsText.text = "20000"
        progressBar.progress = 10

        // Переход на moc_screen
        btnSwitch.setOnClickListener {
            val intent = Intent(this, moc_screen::class.java)

            // Если нужно передать данные:
            // intent.putExtra("totalMedals", "20000")
            // intent.putExtra("progress", 65)

            startActivity(intent)
        }

        // Переход на vc_screen
        btnVc.setOnClickListener {
            val intent = Intent(this, vc_screen::class.java)

            // Передача данных (опционально)
            intent.putExtra("totalMedals", totalMedalsText.text.toString())
            intent.putExtra("progress", progressBar.progress)

            startActivity(intent)
        }
    }
}