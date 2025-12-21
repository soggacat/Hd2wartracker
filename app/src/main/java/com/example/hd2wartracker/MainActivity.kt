package com.example.hd2wartracker

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : Activity() {

    private val MOC_PREFS = "moc_prefs"
    private val PRESSED_KEY = "pressed_buttons"
    private val MEDALS_KEY = "moc_medals"

    private val TOTAL_MOC = 18
    private val MAX_MOC_MEDALS = 641

    private lateinit var mocProgressBar: ProgressBar
    private lateinit var mocCompletedText: TextView
    private lateinit var mocMedalsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mocProgressBar = findViewById(R.id.moc_card_ProgressBar)
        mocCompletedText = findViewById(R.id.moc_comp)
        mocMedalsText = findViewById(R.id.moc_medals)

        val btnMoc = findViewById<ImageButton>(R.id.moc_button)
        val btnVc = findViewById<ImageButton>(R.id.vc_button)
        val btnUl = findViewById<ImageButton>(R.id.ul_button)
        val btnBj = findViewById<ImageButton>(R.id.bj_button)


        btnMoc.setOnClickListener {
            startActivity(Intent(this, moc_screen::class.java))
        }

        btnVc.setOnClickListener {
            startActivity(Intent(this, vc_screen::class.java))
        }

        btnUl.setOnClickListener {
            startActivity(Intent(this, ul_screen::class.java))
        }

        btnBj.setOnClickListener {
            startActivity(Intent(this, bj_screen::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        updateMocCard()
    }

    private fun updateMocCard() {
        val prefs = getSharedPreferences(MOC_PREFS, MODE_PRIVATE)

        val pressed = prefs.getStringSet(PRESSED_KEY, emptySet()) ?: emptySet()
        val completed = pressed.size
        val progress = completed * 100 / TOTAL_MOC


        val medals = prefs.getInt(MEDALS_KEY, 0)

        mocCompletedText.text = "Completed $completed/$TOTAL_MOC"
        mocMedalsText.text = "Medals: $medals/$MAX_MOC_MEDALS"

        animateProgressBar(mocProgressBar, progress, 300)
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
}
