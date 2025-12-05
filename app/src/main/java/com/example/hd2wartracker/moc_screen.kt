package com.example.hd2wartracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton

class moc_screen : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.moc_lay)
        val btnSwitch = findViewById<ImageButton>(R.id.moc_backbutton) // Новая кнопка

        btnSwitch.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)

            // Если нужно передать данные:
            // intent.putExtra("totalMedals", "20000")
            // intent.putExtra("progress", 65)

            startActivity(intent)

        }
        // Получение данных (если передавали)
        // val medals = intent.getStringExtra("totalMedals")
        // val progress = intent.getIntExtra("progress", 0)
    }
}