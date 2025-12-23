package com.example.hd2wartracker

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar

class bj_screen : Activity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var progressBar: ProgressBar

    private val pressed = mutableSetOf<Int>()
    private var totalMedals = 0

    private val prefsName = "bj_prefs"
    private val pressedKey = "pressed_buttons"
    private val medalsKey = "bj_medals"

    private val TOTAL = 18

    private val medalsPerButton = listOf(
        7, 8, 35, 30, 2, 45,
        45, 7, 55, 25, 12, 40,
        65, 55, 65, 110, 25, 32
    )

    private val pressedImages = listOf(
        R.drawable.moc_p1_item5d,
        R.drawable.bj_p1_2d,
        R.drawable.bj_p1_3d,
        R.drawable.bj_p1_4d,
        R.drawable.bj_p1_5d,
        R.drawable.bj_p1_6d,


        R.drawable.bj_p2_1d,
        R.drawable.bj_p2_2d,
        R.drawable.bj_p2_3d,
        R.drawable.bj_p2_4d,
        R.drawable.moc_p1_item5d,
        R.drawable.bj_p2_6d,


        R.drawable.bj_p3_1d,
        R.drawable.bj_p3_2d,
        R.drawable.bj_p3_3d,
        R.drawable.bj_p3_4d,
        R.drawable.bj_p3_5d,
        R.drawable.moc_p1_item5d
    )

    private val normalImages = listOf(
        R.drawable.moc_p1_5,
        R.drawable.bj_p1_2,
        R.drawable.bj_p1_3,
        R.drawable.bj_p1_4,
        R.drawable.bj_p1_5,
        R.drawable.bj_p1_6,


        R.drawable.bj_p2_1,
        R.drawable.bj_p2_2,
        R.drawable.bj_p2_3,
        R.drawable.bj_p2_4,
        R.drawable.moc_p2_1,
        R.drawable.bj_p2_6,


        R.drawable.bj_p3_1,
        R.drawable.bj_p3_2,
        R.drawable.bj_p3_3,
        R.drawable.bj_p3_4,
        R.drawable.bj_p3_5,
        R.drawable.moc_p3_5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bj_lay)

        progressBar = findViewById(R.id.bj_progressbar)

        val btnBack = findViewById<ImageButton>(R.id.moc_backbutton)
        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        buttons = listOf(
            findViewById(R.id.bj_p1_1),
            findViewById(R.id.bj_p1_2),
            findViewById(R.id.bj_p1_3),
            findViewById(R.id.bj_p1_4),
            findViewById(R.id.bj_p1_5),
            findViewById(R.id.bj_p1_6),

            findViewById(R.id.bj_p2_1),
            findViewById(R.id.bj_p2_2),
            findViewById(R.id.bj_p2_3),
            findViewById(R.id.bj_p2_4),
            findViewById(R.id.bj_p2_5),
            findViewById(R.id.bj_p2_6),

            findViewById(R.id.bj_p3_1),
            findViewById(R.id.bj_p3_2),
            findViewById(R.id.bj_p3_3),
            findViewById(R.id.bj_p3_4),
            findViewById(R.id.bj_p3_5),
            findViewById(R.id.bj_p3_6)
        )

        loadState()
        restoreUI()

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                handleButtonPress(index)
            }
        }
    }

    private fun handleButtonPress(index: Int) {
        if (pressed.contains(index)) {
            pressed.remove(index)
            buttons[index].setImageResource(normalImages[index])
        } else {
            pressed.add(index)
            buttons[index].setImageResource(pressedImages[index])
        }

        recalcMedals()
        updateProgress(true)
    }

    private fun recalcMedals() {
        totalMedals = pressed.sumOf { medalsPerButton[it] }
    }

    private fun updateProgress(animated: Boolean) {
        val progress = pressed.size * 100 / TOTAL
        if (animated) animateProgress(progress) else progressBar.progress = progress
    }

    private fun animateProgress(to: Int) {
        ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, to)
            .setDuration(300)
            .start()
    }

    private fun restoreUI() {
        buttons.forEachIndexed { index, button ->
            if (pressed.contains(index)) {
                button.setImageResource(pressedImages[index])
            } else {
                button.setImageResource(normalImages[index])
            }
        }
        recalcMedals()
        updateProgress(false)
    }

    private fun saveState() {
        val prefs = getSharedPreferences(prefsName, MODE_PRIVATE)
        prefs.edit()
            .putStringSet(pressedKey, pressed.map { it.toString() }.toSet())
            .putInt(medalsKey, totalMedals)
            .apply()
    }

    private fun loadState() {
        val prefs = getSharedPreferences(prefsName, MODE_PRIVATE)
        pressed.clear()

        val saved = prefs.getStringSet(pressedKey, emptySet())!!
            .mapNotNull { it.toIntOrNull() }
            .filter { it in 0 until medalsPerButton.size }

        pressed.addAll(saved)
        recalcMedals()
    }


    override fun onPause() {
        super.onPause()
        saveState()
    }
}
