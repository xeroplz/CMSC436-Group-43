package com.example.mafia43

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HowToPlayActivity: AppCompatActivity() {
    companion object {

    }

    private lateinit var mBackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_htp)

        mBackButton = findViewById(R.id.htp_back_button)
        mBackButton.setOnClickListener {
            val intent = Intent(this@HowToPlayActivity, TitleActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this@HowToPlayActivity, TitleActivity::class.java)
        startActivity(intent)
    }
}