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

class NightSaveActivity : AppCompatActivity() {
    private lateinit var mContinueButton : Button
    private lateinit var mPlayers : Array<Player>
    private lateinit var mTextView : TextView
    private lateinit var mNightView : TextView
    private lateinit var mBundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_role_confirm)

        /* Get players array from intent Bundle */
        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>

        mContinueButton = findViewById(R.id.nContinue)
        mNightView = findViewById(R.id.nTextView)
        mTextView = findViewById(R.id.nRoleTextView)
        val killedPlayer = intent.getStringExtra("Kill")
        val saved = intent.getBooleanExtra("Saved", false)
        val rand = intent.getIntExtra("Random", 0)
        var alive = intent.getIntExtra("AlivePlayers", mPlayers.size)

        mTextView.setTextSize(20f)
        mNightView.setText("Night Recap")

        if (saved) {
            when (rand) {
                1 -> mTextView.setText("Luckily, the doctor was walking past their house and heard muffled screams. The doctor was able to dig them out of the pile of clothes just in time, but " + killedPlayer + " could not see the person who saved them.")
                2 -> mTextView.setText(killedPlayer + " was rushed to the hospital, where the doctor was able to repair the neck damage.")
                3 -> mTextView.setText("Luckily, the doctor was outside tending to the field and made a path for " + killedPlayer + " to run through before the mafia could catch up.")
                4 -> mTextView.setText("Luckily, the doctor was supposed to pay a visit that night and rang the doorbell, which gave " + killedPlayer + " enough time to free themselves before they were devoured by their own dogs.")
                5 -> mTextView.setText(killedPlayer + " was rushed to the hospital, where they were able to extract the bull blood from their body.")
                6 -> mTextView.setText("Luckily, someone was over " + killedPlayer + "’s house and pushed them out of the way before the tortoise hit their head.")
                7 -> mTextView.setText("Luckily, they were so far gone that they could not even make it out of their own house.")
                8 -> mTextView.setText("Luckily, it was not the mafia that actually came to visit them, but it was actually a close relative. The relative knew the heimlich maneuver and was able to dislodge the grape.")
                9 -> mTextView.setText("Luckily, it turns out that the mafia ran out of milk and honey and instead used butter, which allowed " + killedPlayer + " to slide out and escape.")
                10 -> mTextView.setText("It was a pear. What were you expecting to happen?")
                11 -> mTextView.setText("Luckily, " + killedPlayer + " knew of past stories of mercury pills and did not take the pills.")
                12 -> mTextView.setText("After a few seconds, they calmed down and returned their donkey to their pen.")
                13 -> mTextView.setText("Luckily, " + killedPlayer + " woke up and realized what was happening and spit out the coals in their mouth. Unfortunately, they lost their sense of taste, but at least, they are still alive.")
                14 -> mTextView.setText(killedPlayer + " started to crack some jokes to their tormentor. They said, “Turn me over--I’m done on this side”. This amused their tormentor and the tormentor ended up letting them go, saying “same time next week and at your place or mine?”")
                15 -> mTextView.setText("Luckily, " + killedPlayer + " was a snake charmer and was able to have the snakes help them out of the pit.")
                16 -> mTextView.setText(killedPlayer + " was rushed to the hospital and the doctor found that they only sustained minor wounds.")
                17 -> mTextView.setText(killedPlayer + " fell on the floor and only sustained minor injuries.")
                18 -> mTextView.setText("Luckily, the doctor was near by and saw the weird scene going on and moved the rug out of the way before the horses could trample them.")
                19 -> mTextView.setText("Luckily, " + killedPlayer + " was in the bed next to the bed that the mafia set on fire and was able to escape their house before they caught on fire.")
                20 -> mTextView.setText("Luckily, " + killedPlayer + " was taller than the barrel and was able to stand up in the wine. Now, they had a large supply of wine and are still alive.")
                21 -> mTextView.setText("But luckily for " + killedPlayer + ", it was not the year 1518. And they just stopped dancing when they started getting tired.")
                22 -> mTextView.setText("Luckily, they were able to catch themselves from falling.")
            }
            mTextView.setText(mTextView.text.toString() + "\n\n" + alive.toString() + " players still remain...")
        } else {
            alive--
            mTextView.setText(killedPlayer + " has died and was not saved by the doctor...\n\n" + alive.toString() + " players remain...")
            for(player in mPlayers) {
                if(player.name() == killedPlayer) {
                    player.die()
                }
            }
        }

        mContinueButton.setOnClickListener {
            if (alive <= 2) {
                val endIntent = Intent(this@NightSaveActivity, EndActivity::class.java)
                endIntent.putExtra("Mafia", true)
                startActivity(endIntent)
            } else {
                /* Creating intent for Voting Activity using players data.
                Add players array as extra to intent. */
                val voteIntent = Intent(this@NightSaveActivity, Voting::class.java)
                /* You have to create a Bundle to pass the Player array */
                val args = Bundle()
                args.putSerializable("playersArr", mPlayers as Serializable)
                voteIntent.putExtra("Bundle", args)
                voteIntent.putExtra("AlivePlayers", alive)
                startActivity(voteIntent)
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@NightSaveActivity)

        builder.setTitle("Quit Game")
        builder.setMessage("Are you sure you want to end the game?")
        builder.setNegativeButton("Yes", DialogInterface.OnClickListener{ _, _ ->
            val restartIntent = Intent(this@NightSaveActivity, TitleActivity::class.java)
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
        const val TAG = "night_recap_activity"
        const val MAFIA = 1
        const val DOCTOR = 2
        const val DETECTIVE = 3
        const val CIVILIAN = 4
    }
}