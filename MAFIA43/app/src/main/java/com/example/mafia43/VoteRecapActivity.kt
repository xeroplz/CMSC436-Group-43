package com.example.mafia43

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VoteRecapActivity : AppCompatActivity() {
    lateinit var votedOut : String
    lateinit var mTextView : TextView
    lateinit var continueButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.vote_recap)
        mTextView = findViewById(R.id.voted_out_text)

        votedOut = intent.getStringExtra("VotedOff").toString()

        mTextView.text = "$votedOut was voted off."

        val nightIntent = Intent(this@VoteRecapActivity, NightActivity::class.java)
        val args = Bundle()
        args.putSerializable("playersArr", intent.getSerializableExtra("playersArr"))

        continueButton = findViewById(R.id.recap_next_button)

        continueButton.setOnClickListener {
            nightIntent.putExtra("Bundle", args)
            nightIntent.putExtra("AlivePlayers", intent.getStringExtra("AlivePlayers"))
            nightIntent.putExtra("Role", 1)
            nightIntent.putExtra("Kill", "")
            nightIntent.putExtra("Save", "")
        }
    }
}