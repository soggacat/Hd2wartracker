package com.example.hd2wartracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val totalMedalsText = findViewById<TextView>(R.id.totalMedals)
        val progressBar = findViewById<ProgressBar>(R.id.mainProgressBar)
        val btnSwitch = findViewById<ImageButton>(R.id.moc_button)
        val btnVc = findViewById<ImageButton>(R.id.vc_button)
        val btnUl = findViewById<ImageButton>(R.id.url_button)

        totalMedalsText.text = "20000"
        progressBar.progress = 10


        btnSwitch.setOnClickListener {
            val intent = Intent(this, moc_screen::class.java)


            startActivity(intent)
        }

        btnVc.setOnClickListener {
            val intent = Intent(this, vc_screen::class.java)

            intent.putExtra("totalMedals", totalMedalsText.text.toString())
            intent.putExtra("progress", progressBar.progress)

            startActivity(intent)
        }


        btnUl.setOnClickListener {
            val intent = Intent(this, ul_screen::class.java)

            intent.putExtra("totalMedals", totalMedalsText.text.toString())
            intent.putExtra("progress", progressBar.progress)

            startActivity(intent)
        }
    }
}