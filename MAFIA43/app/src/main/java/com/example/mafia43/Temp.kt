package com.example.mafia43

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import java.io.Serializable

/* I created this as an placeholder activity so that I can use it to
 guide my implementation of Voting.kt */

class Temp: AppCompatActivity()  {

    /* "players" Array will represent the array of player names we get from initial activity */
    lateinit var players : Array<Player>

    private lateinit var mVoteButton : Button
    private lateinit var mNightButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp)

        mVoteButton = findViewById(R.id.vote_button)
        mNightButton = findViewById(R.id.night_button)

        /* Just placeholder strings for now */
        players = arrayOf<Player>(
            Player("Naomi", 1),
            Player("Julian", 2),
            Player("Matt", 3),
            Player("A name", 4),
            Player("Another", 4),
            Player("Player 3", 4)
        )

        mVoteButton.setOnClickListener{
            /* Creating intent for Voting Activity using players data.
            Add players array as extra to intent. */
            val voteIntent = Intent(this@Temp, Voting::class.java)
            /* You have to create a Bundle to pass the Player array */
            val args = Bundle()
            args.putSerializable("playersArr", players as Serializable)
            voteIntent.putExtra("Bundle", args)
            startActivity(voteIntent)
        }

        mNightButton.setOnClickListener{
            val nightIntent = Intent(this@Temp, NightActivity::class.java)
            /* You have to create a Bundle to pass the Player array */
            val args = Bundle()
            args.putSerializable("playersArr", players as Serializable)
            nightIntent.putExtra("Bundle", args)
            nightIntent.putExtra("AlivePlayers", 6)
            nightIntent.putExtra("Role", 1)
            startActivity(nightIntent)
        }

    }
}