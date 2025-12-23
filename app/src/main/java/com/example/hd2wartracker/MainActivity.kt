package com.example.hd2wartracker

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : Activity() {
    // для добавления варбондиков пропиши
    // prefs, medals, max_medals, total
    // и в ui progress, completed, medals

    // ===== PREFS =====

    private val PRESSED_KEY = "pressed_buttons"

    private val MOC_PREFS = "moc_prefs"
    private val VC_PREFS  = "vc_prefs"
    private val UL_PREFS  = "ul_prefs"
    private val BJ_PREFS  = "bj_prefs"

    private val MOC_MEDALS = "moc_medals"
    private val VC_MEDALS  = "vc_medals"
    private val UL_MEDALS  = "ul_medals"
    private val BJ_MEDALS  = "bj_medals"

    // =====ц TOTALS =====
    private val TOTAL_MOC = 18
    private val TOTAL_VC  = 23
    private val TOTAL_UL  = 22
    private val TOTAL_BJ  = 18

    private val MAX_MOC_MEDALS = 641
    private val MAX_VC_MEDALS  = 734
    private val MAX_UL_MEDALS  = 829
    private val MAX_BJ_MEDALS  = 663

    // ===== UI (MOC) =====
    private lateinit var mocProgress: ProgressBar
    private lateinit var mocCompleted: TextView
    private lateinit var mocMedals: TextView

    // ===== UI (VC) =====
    private lateinit var vcProgress: ProgressBar
    private lateinit var vcCompleted: TextView
    private lateinit var vcMedals: TextView

    // ===== UI (UL) =====
    private lateinit var ulProgress: ProgressBar
    private lateinit var ulCompleted: TextView
    private lateinit var ulMedals: TextView

    // ===== UI (BJ) =====
    private lateinit var bjProgress: ProgressBar
    private lateinit var bjCompleted: TextView
    private lateinit var bjMedals: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === MOC ===
        mocProgress  = findViewById(R.id.moc_card_ProgressBar)
        mocCompleted = findViewById(R.id.moc_comp)
        mocMedals    = findViewById(R.id.moc_medals)

        // === VC ===
        vcProgress  = findViewById(R.id.vc_card_ProgressBar)
        vcCompleted = findViewById(R.id.vc_comp)
        vcMedals    = findViewById(R.id.vc_medals)

        // === UL ===
        ulProgress  = findViewById(R.id.ul_card_ProgressBar)
        ulCompleted = findViewById(R.id.ul_comp)
        ulMedals    = findViewById(R.id.ul_medals)

        // === BJ ===
        bjProgress  = findViewById(R.id.bj_card_ProgressBar)
        bjCompleted = findViewById(R.id.bj_comp)
        bjMedals    = findViewById(R.id.bj_medals)

        // === Кнопки ===
        findViewById<ImageButton>(R.id.moc_button)
            .setOnClickListener { startActivity(Intent(this, moc_screen::class.java)) }

        findViewById<ImageButton>(R.id.vc_button)
            .setOnClickListener { startActivity(Intent(this, vc_screen::class.java)) }

        findViewById<ImageButton>(R.id.ul_button)
            .setOnClickListener { startActivity(Intent(this, ul_screen::class.java)) }

        findViewById<ImageButton>(R.id.bj_button)
            .setOnClickListener { startActivity(Intent(this, bj_screen::class.java)) }
    }

    override fun onResume() {
        super.onResume()

        updateCard(
            MOC_PREFS, MOC_MEDALS, TOTAL_MOC, MAX_MOC_MEDALS,
            mocProgress, mocCompleted, mocMedals
        )

        updateCard(
            VC_PREFS, VC_MEDALS, TOTAL_VC, MAX_VC_MEDALS,
            vcProgress, vcCompleted, vcMedals
        )

        updateCard(
            UL_PREFS, UL_MEDALS, TOTAL_UL, MAX_UL_MEDALS,
            ulProgress, ulCompleted, ulMedals
        )

        updateCard(
            BJ_PREFS, BJ_MEDALS, TOTAL_BJ, MAX_BJ_MEDALS,
            bjProgress, bjCompleted, bjMedals
        )
    }

    // ===== ОБЩИЙ МЕТОД =====
    private fun updateCard(
        prefsName: String,
        medalsKey: String,
        total: Int,
        maxMedals: Int,
        progressBar: ProgressBar,
        completedText: TextView,
        medalsText: TextView
    ) {
        val prefs = getSharedPreferences(prefsName, MODE_PRIVATE)

        val pressed = prefs.getStringSet(PRESSED_KEY, emptySet()) ?: emptySet()
        val completed = pressed.size
        val progress = completed * 100 / total
        val medals = prefs.getInt(medalsKey, 0)

        completedText.text = "Completed $completed/$total"
        medalsText.text = "Medals: $medals/$maxMedals"

        animateProgressBar(progressBar, progress, 300)
    }

    private fun animateProgressBar(
        progressBar: ProgressBar,
        toProgress: Int,
        duration: Long
    ) {
        ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, toProgress)
            .setDuration(duration)
            .start()
    }
}

