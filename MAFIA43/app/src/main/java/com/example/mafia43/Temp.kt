package com.example.mafia43

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

/* I created this as an placeholder activity so that I can use it to
 guide my implementation of Voting.kt */

class Temp: AppCompatActivity()  {

    /* "players" Array will represent the array of player names we get from initial activity */
    lateinit var players : Array<String>

    private lateinit var mVoteButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp)

        mVoteButton = findViewById(R.id.vote_button)

        /* Just placeholder strings for now */
        players = arrayOf<String>(
            "Naomi",
            "Julian",
            "Matt",
            "A name",
            "Another",
            "Player 3"
        )

        mVoteButton.setOnClickListener{
            /* Creating intent for Voting Activity using players data.
            Add players array as extra to intent. */
            val voteIntent = Intent(this@Temp, Voting::class.java)
            voteIntent.putExtra("playersArr", players)
            startActivity(voteIntent)
        }

    }
}