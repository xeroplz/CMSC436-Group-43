package com.example.mafia43

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

/* I created this as an placeholder activity so that I can use it to
 guide my implementation of Voting.kt */

class Temp: AppCompatActivity()  {

    /* "players" Array will represent the array of player names we get from initial activity */
    lateinit var players : Array<Player>

    private lateinit var mVoteButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp)

        mVoteButton = findViewById(R.id.vote_button)

        val naomi = Player("naomi", 0);
        val j = Player("julian", 0)
        val m = Player("matt", 0)
        val n = Player("name", 0)
        val three = Player("player3", 0)
        val another = Player("another", 0)
        /* Just placeholder strings for now */
        players = arrayOf<Player>(
            naomi,
            j,
            m,
            n,
            three,
            another
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

    }
}