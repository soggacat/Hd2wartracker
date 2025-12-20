package com.example.hd2wartracker

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar

class vc_screen : Activity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var progressBar: ProgressBar

    private val pressed = mutableSetOf<Int>()
    private var totalMedals = 0

    private val prefsName = "prefs"
    private val pressedKey = "pressed_buttons"
    private val medalsKey = "medals"

    private val TOTAL = 24

    private val medalsPerButton = listOf(
        40, 20, 30, 8, 7, 2, 20, 8,
        12, 8, 40, 55, 45, 2, 2, 2,
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
        R.drawable.ph,
        R.drawable.ph,
        R.drawable.ph
    )

    private val normalImages = listOf(
        R.drawable.vc_p1_1,
        R.drawable.vc_p1_2,
        R.drawable.vc_p1_3,
        R.drawable.vc_p1_4,
        R.drawable.vc_p1_5,
        R.drawable.vc_p1_6,
        R.drawable.vc_p1_7,
        R.drawable.vc_p1_8,

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
        setContentView(R.layout.vc_lay)

        progressBar = findViewById(R.id.vc_progressbar)

        val btnBack = findViewById<ImageButton>(R.id.moc_backbutton)
        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        buttons = listOf(
            findViewById(R.id.vc_p1_item1),
            findViewById(R.id.vc_p1_item2),
            findViewById(R.id.vc_p1_item3),
            findViewById(R.id.vc_p1_item4),
            findViewById(R.id.vc_p1_item5),
            findViewById(R.id.vc_p1_item6),
            findViewById(R.id.vc_p1_item7),
            findViewById(R.id.vc_p1_item8),

            findViewById(R.id.p2_item_center2),
            findViewById(R.id.p2_item1),
            findViewById(R.id.p2_item_center),
            findViewById(R.id.p2_item5),
            findViewById(R.id.p2_item2),
            findViewById(R.id.p2_item4),
            findViewById(R.id.p2_item_center3),
            findViewById(R.id.p2_item3),

            findViewById(R.id.p3_item1),
            findViewById(R.id.p3_item2),
            findViewById(R.id.p3_item3),
            findViewById(R.id.p3_item4),
            findViewById(R.id.p3_item5),
            findViewById(R.id.p3_item_center)
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
