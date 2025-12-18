package com.example.hd2wartracker

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar

class moc_screen : Activity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var progressBar: ProgressBar

    private val pressed = mutableSetOf<Int>()
    private var totalMedals = 0

    private val prefsName = "moc_prefs"
    private val pressedKey = "pressed_buttons"
    private val medalsKey = "moc_medals"

    private val TOTAL = 18

    // 💰 Медали за кнопку
    private val medalsPerButton = listOf(
        25, 25, 30, 40, 35, 50,
        20, 20, 25, 40, 30, 35,
        50, 45, 60, 80, 55, 70
    )

    private val pressedImages = listOf(
        R.drawable.moc_p1_item1d,
        R.drawable.moc_p1_item2d,
        R.drawable.moc_p1_item4d,
        R.drawable.moc_p1_item3d,
        R.drawable.moc_p1_item5d,
        R.drawable.moc_p1_item6d,
        R.drawable.moc_p2_1d,
        R.drawable.moc_p2_2d,
        R.drawable.moc_p2_3d,
        R.drawable.moc_p2_6d,
        R.drawable.moc_p2_4d,
        R.drawable.moc_p2_5d,
        R.drawable.moc_p3_1d,
        R.drawable.moc_p3_2d,
        R.drawable.moc_p2_1d,
        R.drawable.moc_p3_6d,
        R.drawable.moc_p3_4d,
        R.drawable.moc_p3_5d,
    )

    private val normalImages = listOf(
        R.drawable.moc_p1_1,
        R.drawable.moc_p1_2,
        R.drawable.moc_p1_4,
        R.drawable.moc_p1_3,
        R.drawable.moc_p1_5,
        R.drawable.moc_p1_6,
        R.drawable.moc_p2_1,
        R.drawable.moc_p2_2,
        R.drawable.moc_p2_3,
        R.drawable.moc_p2_6,
        R.drawable.moc_p2_4,
        R.drawable.moc_p2_5,
        R.drawable.moc_p3_1,
        R.drawable.moc_p3_4,
        R.drawable.moc_p3_5,
        R.drawable.moc_p3_3,
        R.drawable.moc_p3_2,
        R.drawable.moc_p3_6,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.moc_lay)

        progressBar = findViewById(R.id.moc_progressbar)

        val btnBack = findViewById<ImageButton>(R.id.moc_backbutton)
        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        buttons = listOf(
            findViewById(R.id.p1_item1),
            findViewById(R.id.p1_item2),
            findViewById(R.id.p1_item3),
            findViewById(R.id.p1_item_center),
            findViewById(R.id.p1_item4),
            findViewById(R.id.p1_item5),
            findViewById(R.id.p2_item1),
            findViewById(R.id.p2_item2),
            findViewById(R.id.p2_item3),
            findViewById(R.id.p2_item_center),
            findViewById(R.id.p2_item4),
            findViewById(R.id.p2_item5),
            findViewById(R.id.p3_item1),
            findViewById(R.id.p3_item2),
            findViewById(R.id.p3_item3),
            findViewById(R.id.p3_item_center),
            findViewById(R.id.p3_item4),
            findViewById(R.id.p3_item5)
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

    // Пересчет медалей на основе pressed
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
