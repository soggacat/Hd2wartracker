package com.example.hd2wartracker

import android.app.Activity
import android.os.Bundle
import android.widget.ImageButton

class moc_screen : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.moc_lay)

        // Получение данных (если передавали)
        // val medals = intent.getStringExtra("totalMedals")
        // val progress = intent.getIntExtra("progress", 0)
    }
}