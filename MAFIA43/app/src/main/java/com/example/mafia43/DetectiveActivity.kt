package com.example.mafia43

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class DetectiveActivity : AppCompatActivity() {

    private lateinit var mContinueButton : Button
    private lateinit var mPlayers : Array<Player>
    private lateinit var mTextView : TextView
    private lateinit var mDetectiveTextView : TextView
    private lateinit var mBundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_role_confirm)

        /* Get players array from intent Bundle */
        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>
        mContinueButton = findViewById(R.id.nContinue)
        mDetectiveTextView = findViewById(R.id.nTextView)
        mTextView = findViewById(R.id.nRoleTextView)
        val check = intent.getStringExtra("Check")

        mDetectiveTextView.setText("DETECTIVE REPORT")

        for (player in mPlayers) {
            if (player.name() == check) {
                if (player.role() == MAFIA) {
                    mTextView.setText(player.name() + " is a Mafia member!")
                } else {
                    mTextView.setText(player.name() + " is not a Mafia member.")
                }
            }
        }

        mContinueButton.setOnClickListener{
            val nightIntent = Intent(this@DetectiveActivity, NightRecapActivity::class.java)
            /* You have to create a Bundle to pass the Player array */
            val args = Bundle()
            args.putSerializable("playersArr", mPlayers as Serializable)
            nightIntent.putExtra("Bundle", args)
            nightIntent.putExtra("AlivePlayers", intent.getIntExtra("AlivePlayers", mPlayers.size))
            nightIntent.putExtra("Kill", intent.getStringExtra("Kill"))
            nightIntent.putExtra("Save", intent.getStringExtra("Save"))

            val builder: AlertDialog.Builder = AlertDialog.Builder(this@DetectiveActivity)

            builder.setTitle("Please place phone back in the center")
            builder.setMessage("when ready, press OK")
            builder.setCancelable(false);
            builder.setPositiveButton("OK", DialogInterface.OnClickListener{ _,_ ->
                // go to next Night Activity
                startActivity(nightIntent)

            })

            val alertDialog = builder.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.setOnShowListener {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GRAY)
            }

            alertDialog.show()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@DetectiveActivity)

        builder.setTitle("Quit Game")
        builder.setMessage("Are you sure you want to end the game?")
        builder.setNegativeButton("Yes", DialogInterface.OnClickListener{ _, _ ->
            val restartIntent = Intent(this@DetectiveActivity, TitleActivity::class.java)
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

    companion object{
        const val TAG = "detective_activity"
        const val MAFIA = 1
        const val DOCTOR = 2
        const val DETECTIVE = 3
        const val CIVILIAN = 4
    }
}