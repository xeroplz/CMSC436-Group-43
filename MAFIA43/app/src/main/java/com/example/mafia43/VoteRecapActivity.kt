package com.example.mafia43

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.appcompat.app.AlertDialog

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

        continueButton = findViewById(R.id.recap_next_button)

        continueButton.setOnClickListener {
            val nightIntent = Intent(this@VoteRecapActivity, NightActivity::class.java)
            val args = Bundle()
            val currBundle = intent.getBundleExtra("Bundle")
            val playersArray = currBundle?.getSerializable("playersArr") as Array<Player>

            args.putSerializable("playersArr", playersArray)

            nightIntent.putExtra("Bundle", args)
            nightIntent.putExtra("AlivePlayers", intent.getIntExtra("AlivePlayers", -1))
            nightIntent.putExtra("Role", 1)
            nightIntent.putExtra("Kill", "")
            nightIntent.putExtra("Save", "")

            startActivity(nightIntent)
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@VoteRecapActivity)

        builder.setTitle("Quit Game")
        builder.setMessage("Are you sure you want to end the game?")
        builder.setNegativeButton("Yes", DialogInterface.OnClickListener{ _, _ ->
            val restartIntent = Intent(this@VoteRecapActivity, TitleActivity::class.java)
            startActivity(restartIntent)
        })
        builder.setPositiveButton("No", null)

        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY)
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GRAY)
        }
        alertDialog.show()
    }
}