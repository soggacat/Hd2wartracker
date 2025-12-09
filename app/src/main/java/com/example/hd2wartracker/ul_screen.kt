package com.example.hd2wartracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.animation.ObjectAnimator

class ul_screen : Activity() {

    private lateinit var buttons:List<ImageButton>
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ul_layout)

        val btnSwitch = findViewById<ImageButton>(R.id.moc_backbutton)


        btnSwitch.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
