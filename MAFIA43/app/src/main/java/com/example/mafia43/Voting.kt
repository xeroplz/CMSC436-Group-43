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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.voting_selection)

        Log.i("tag", "here")
        /* Get players array from intent */
        extras = intent.extras!!

        /* Create layout inflater for selection of players */
        mInflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        mInflater.inflate(R.layout.voting_selection, player_list, false)
        
    }
}