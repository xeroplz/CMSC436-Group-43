package com.example.mafia43

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EndActivity : AppCompatActivity() {

    private lateinit var mNewButton: Button
    private lateinit var mSameButton: Button
    private lateinit var mReturnButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mafiaWin = intent.getBooleanExtra("Mafia", false)

        if (mafiaWin) {
            setContentView(R.layout.civilian_win)
            val textView = findViewById(R.id.cWinText) as TextView
            textView.setText("Mafia Wins...")
        } else {
            setContentView(R.layout.civilian_win)
        }

        mSameButton = findViewById(R.id.button)
        mNewButton = findViewById(R.id.button2)
        mReturnButton = findViewById(R.id.titleButton)

        mSameButton.setOnClickListener {
            // Add in some functionality to reassign the roles
        }

        mNewButton.setOnClickListener {
            // Start the character creation activity!
            val intent = Intent(this@EndActivity, RoleAssignmentActivity::class.java)
            startActivity(intent)
        }

        mReturnButton.setOnClickListener {
            // Start the title screen activity!
            val intent = Intent(this@EndActivity, TitleActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}