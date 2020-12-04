package com.example.mafia43

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NightRecapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_role_confirm)
    }

    companion object{
        const val TAG = "night_recap_activity"
        const val MAFIA = 1
        const val DOCTOR = 2
        const val DETECTIVE = 3
        const val CIVILIAN = 4
    }
}