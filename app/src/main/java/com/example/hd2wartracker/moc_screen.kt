package com.example.hd2wartracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.animation.ObjectAnimator

class moc_screen : Activity() {

    private lateinit var buttons:List<ImageButton>
    private lateinit var progressBar: ProgressBar

    private val pressed = mutableSetOf<Int>()

    private val pressedImages = listOf(
        R.drawable.p1_item1d,
        R.drawable.p1_item2d,
        R.drawable.p1_item3d,
        R.drawable.p1_item_centerd  ,
        R.drawable.p1_item4d,
        R.drawable.p1_item5d
    )
    private val normalImages = listOf(
        R.drawable.moc_p1_1,
        R.drawable.moc_p1_2,
        R.drawable.moc_p1_4,
        R.drawable.moc_p1_3  ,
        R.drawable.moc_p1_5,
        R.drawable.moc_p1_6
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.moc_lay)

        val btnSwitch = findViewById<ImageButton>(R.id.moc_backbutton)
        progressBar = findViewById(R.id.moc_progressbar)

        buttons = listOf(
            findViewById<ImageButton>(R.id.p1_item1),
            findViewById<ImageButton>(R.id.p1_item2),
            findViewById<ImageButton>(R.id.p1_item3),
            findViewById<ImageButton>(R.id.p1_item_center),
            findViewById<ImageButton>(R.id.p1_item4),
            findViewById<ImageButton>(R.id.p1_item5),
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
