package com.example.hd2wartracker

import android.app.Activity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация элементов
        val totalMedalsText = findViewById<TextView>(R.id.totalMedals)
        val progressBar = findViewById<ProgressBar>(R.id.mainProgressBar)

        // Установка значений
        totalMedalsText.text = "20000"
        progressBar.progress = 65
    }
}