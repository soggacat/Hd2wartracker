package com.example.hd2wartracker

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar

class ul_screen : Activity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var progressBar: ProgressBar

    private val pressed = mutableSetOf<Int>()
    private var totalMedals = 0

    private val prefsName = "ul_prefs"
    private val pressedKey = "pressed_buttons"
    private val medalsKey = "ul_medals"

    private val TOTAL = 20
    private val medalsPerButton = listOf(
        40, 20, 30, 8, 7, 2, 7, 2,
        12, 8, 40, 55, 45, 2,
        110, 25, 32, 55, 65, 65
    )

    private val pressedImages = listOf(
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,


        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,


        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph
    )

    private val normalImages = listOf(
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,


        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,


        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ul_layout)

        progressBar = findViewById(R.id.ul_progressbar)

        val btnBack = findViewById<ImageButton>(R.id.moc_backbutton)
        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        buttons = listOf(
            findViewById(R.id.ul_p1_1),
            findViewById(R.id.ul_p1_2),
            findViewById(R.id.ul_p1_3),
            findViewById(R.id.ul_p1_4),
            findViewById(R.id.ul_p1_5),
            findViewById(R.id.ul_p1_6),
            findViewById(R.id.ul_p1_7),
            findViewById(R.id.ul_p1_8),

            findViewById(R.id.ul_p2_1),
            findViewById(R.id.ul_p2_2),
            findViewById(R.id.ul_p2_3),
            findViewById(R.id.ul_p2_4),
            findViewById(R.id.ul_p2_5),
            findViewById(R.id.ul_p2_6),

            findViewById(R.id.ul_p3_1),
            findViewById(R.id.ul_p3_2),
            findViewById(R.id.ul_p3_3),
            findViewById(R.id.ul_p3_4),
            findViewById(R.id.ul_p3_5),
            findViewById(R.id.ul_p3_6)
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
        pressed.addAll(
            prefs.getStringSet(pressedKey, emptySet())!!.map { it.toInt() }
        )
        recalcMedals()
    }

    override fun onPause() {
        super.onPause()
        saveState()
    }
}
