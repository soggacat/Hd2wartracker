package com.example.hd2wartracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView

class m_card : Activity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var completedText: TextView

    private val prefsName = "moc_prefs"
    private val prefsKey = "pressed_buttons"

    private val totalItems = 18

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_m)

        progressBar = findViewById(R.id.moc_card_ProgressBar)
        completedText = findViewById(R.id.moc_comp)

        val mocButton = findViewById<ImageButton>(R.id.moc_button)
        mocButton.setOnClickListener {
            startActivity(Intent(this, moc_screen::class.java))
        }

        loadProgress()
    }

    override fun onResume() {
        super.onResume()
        loadProgress()
    }

    private fun loadProgress() {
        val prefs = getSharedPreferences(prefsName, MODE_PRIVATE)
        val saved = prefs.getStringSet(prefsKey, emptySet()) ?: emptySet()

        val completed = saved.size
        val percent = (completed * 100f / totalItems).toInt()

        progressBar.progress = percent
        completedText.text = "Completed: $completed/$totalItems"
    }
}
