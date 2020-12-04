package com.example.mafia43

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VoteRecapActivity : AppCompatActivity() {
    lateinit var votedOut : String
    lateinit var mTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.vote_recap)
        mTextView = findViewById(R.id.voted_out_text)

        votedOut = intent.getStringExtra("VotedOff").toString()

        mTextView.text = "$votedOut was voted off."
    }
}