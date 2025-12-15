package com.example.hd2wartracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.animation.ObjectAnimator

class moc_screen : Activity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var progressBar: ProgressBar

    private val pressed = mutableSetOf<Int>()

    private val prefsName = "moc_prefs"
    private val prefsKey = "pressed_buttons"

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

        val btnSwitch = findViewById<ImageButton>(R.id.moc_backbutton)
        progressBar = findViewById(R.id.moc_progressbar)

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
        restoreButtons()

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                handleButtonPress(index, button)
            }
        }

        btnSwitch.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun handleButtonPress(index: Int, button: ImageButton) {
        if (pressed.contains(index)) {
            pressed.remove(index)
            button.setImageResource(normalImages[index])
        } else {
            pressed.add(index)
            button.setImageResource(pressedImages[index])
        }

        updateProgress(true)
    }

    private fun updateProgress(animated: Boolean) {
        val part = 100f / buttons.size
        val newProgress = (pressed.size * part).toInt()

        if (animated) {
            animateProgressBar(progressBar, newProgress, 300)
        } else {
            progressBar.progress = newProgress
        }
    }

    private fun restoreButtons() {
        buttons.forEachIndexed { index, button ->
            if (pressed.contains(index)) {
                button.setImageResource(pressedImages[index])
            } else {
                button.setImageResource(normalImages[index])
            }
        }
        updateProgress(false)
    }

    private fun animateProgressBar(
        progressBar: ProgressBar,
        toProgress: Int,
        duration: Long
    ) {
        val animator = ObjectAnimator.ofInt(
            progressBar,
            "progress",
            progressBar.progress,
            toProgress
        )
        animator.duration = duration
        animator.start()
    }

    private fun saveState() {
        val prefs = getSharedPreferences(prefsName, MODE_PRIVATE)
        prefs.edit()
            .putStringSet(
                prefsKey,
                pressed.map { it.toString() }.toSet()
            )
            .apply()
    }

    private fun loadState() {
        val prefs = getSharedPreferences(prefsName, MODE_PRIVATE)
        val saved = prefs.getStringSet(prefsKey, emptySet())!!

        pressed.clear()
        pressed.addAll(saved.map { it.toInt() })
    }

    override fun onPause() {
        super.onPause()
        saveState()
    }
}
