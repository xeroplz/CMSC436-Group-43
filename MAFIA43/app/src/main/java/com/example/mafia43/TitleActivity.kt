package com.example.mafia43

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class TitleActivity : AppCompatActivity() {
    companion object {

    }

    private lateinit var mStartButton : Button
    private lateinit var mTempButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        mStartButton = findViewById(R.id.play_button)

        mStartButton.setOnClickListener {
            // Start the next activity!
            val intent = Intent(this@TitleActivity, RoleAssignmentActivity::class.java)
            startActivity(intent)
        }

        mTempButton = findViewById(R.id.temp_button)

        mTempButton.setOnClickListener {
            val intent = Intent(this@TitleActivity, Temp::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}