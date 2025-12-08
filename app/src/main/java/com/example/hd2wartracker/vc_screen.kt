package com.example.hd2wartracker

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class vc_screen : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vc_layout) // Убедитесь, что у вас есть этот layout

        val btnBack = findViewById<ImageButton>(R.id.moc_backbutton)

        // Пример получения данных из Intent
        val totalMedals = intent.getStringExtra("totalMedals")
        val progress = intent.getIntExtra("progress", 0)

        btnBack.setOnClickListener {
            finish() //
        }
    }
}