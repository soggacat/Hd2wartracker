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
        val btnSwitch = findViewById<ImageButton>(R.id.moc_button) // Новая кнопка



        totalMedalsText.text = "20000"
        progressBar.progress = 65


        btnSwitch.setOnClickListener {

            val intent = Intent(this, moc_screen::class.java)

            // Если нужно передать данные:
            // intent.putExtra("totalMedals", "20000")
            // intent.putExtra("progress", 65)

            startActivity(intent)

        }
    }
}