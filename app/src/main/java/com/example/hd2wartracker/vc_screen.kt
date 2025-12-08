package com.example.hd2wartracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.animation.ObjectAnimator

class vc_screen : Activity() {

    private lateinit var buttons:List<ImageButton>
    private lateinit var progressBar: ProgressBar

    private val pressed = mutableSetOf<Int>()

    private val pressedImages = listOf(
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
        R.drawable.vc_p1_8
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vc_lay)

        val btnSwitch = findViewById<ImageButton>(R.id.moc_backbutton)
        progressBar = findViewById(R.id.vc_progressbar)

        buttons = listOf(
            findViewById<ImageButton>(R.id.vc_p1_item1),
            findViewById<ImageButton>(R.id.vc_p1_item2),
            findViewById<ImageButton>(R.id.vc_p1_item3),
            findViewById<ImageButton>(R.id.vc_p1_item4),
            findViewById<ImageButton>(R.id.vc_p1_item5),
            findViewById<ImageButton>(R.id.vc_p1_item6),
            findViewById<ImageButton>(R.id.vc_p1_item7),
            findViewById<ImageButton>(R.id.vc_p1_item8),
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                handleButtonPress(index, button)

            }
        }

        btnSwitch.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
    private fun handleButtonPress(index: Int, button: ImageButton) {

        if (pressed.contains(index)) {
            pressed.remove(index)
            button.setImageResource(normalImages[index])
        }
        else{
            pressed.add(index)
            button.setImageResource(pressedImages[index])
        }


        val part = 100f / buttons.size
        val newProgress = (pressed.size * part).toInt()

        animateProgressBar(progressBar, newProgress, 300)

    }
    private fun animateProgressBar(progressBar: ProgressBar,toProgress: Int, duration: Long){
        val animator = ObjectAnimator.ofInt(progressBar,"progress",progressBar.progress, toProgress)
        animator.duration = duration
        animator.start()
    }
}
