package com.example.mafia43

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatViewInflater
import com.example.mafia43.R
import kotlinx.android.synthetic.main.voting_selection.*
import android.util.Log


class Voting : AppCompatActivity() {
    lateinit var extras : Bundle
    lateinit var mInflater: LayoutInflater
    lateinit var votingView : TextView
    lateinit var mPlayers : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.voting_selection)

        /* Get players array from intent */
        mPlayers = intent.getStringArrayExtra("playersArr") as Array<String>

        for(i in 0 until mPlayers.size){
            Log.i(TAG,  "player ${mPlayers[i]}")
        }

        /* Create layout inflater for selection of players */
        mInflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        mInflater.inflate(R.layout.voting_selection, player_list, false)

        //addPlayersToList(mInflater, e)
        
    }

    fun addPlayersToList(inflater: LayoutInflater, players: Array<String>){

    }

    companion object{
        const val TAG = "tag"
    }
}