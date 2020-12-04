package com.example.mafia43

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class Voting : AppCompatActivity() {
    lateinit var mTimerText : TextView
    var timeOut : Boolean = false
    lateinit var confirmButton : Button
    lateinit var skipButton : Button
    lateinit var mPlayers : Array<Player>
    lateinit var mBundle : Bundle
    lateinit var votedOff : Player
    var numAlive : Int = 0
    lateinit var mAlivePlayers : Array<Player>
    var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.voting_selection)

        /* Get players array from intent Bundle */
        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>
        numAlive = intent.getIntExtra("AlivePlayers", -1)
        initializeAlivePlayers()



        /* set Timer */
        mTimerText = findViewById(R.id.timer_text)

        object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimerText.text = (millisUntilFinished / 1000).toString()
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {

                mTimerText.setText("Time is up!")
                infoDialog()
            }
        }.start()

        /* Instantiate listView and Adapter

        If you want to recreate this, just create a ListView in the layout resource file,
         and use your listView instead of "player_list" */

        val listView = findViewById<ListView>(R.id.player_list)
        listView.adapter = PlayerListAdapter(
            this,
            R.layout.player_list_item,
            mAlivePlayers
        )

        listView.setBackgroundColor(resources.getColor(R.color.white, null))

        /* position is items position in players array */
        listView.setOnItemClickListener{parent: AdapterView<*>, view: View, position: Int, id: Long ->

            Log.i(TAG, mAlivePlayers[position].name())
            votedOff = mAlivePlayers[position]

        }

        /* When button is clicked, set person voted off to die()
        *  TODO: Maybe I should exclude mafia in voting off list
        *   Only display alive people but make sure to chan
        * */
        confirmButton = findViewById(R.id.confirm_button)

        confirmButton.setOnClickListener{
                // Kill off player in players object arrays
                for (i in mPlayers.indices) {
                    if (mPlayers[i].name().equals(votedOff.name())) {
                        mPlayers[i].die()
                    }
                }

                checkWinCondition() // Goes to next activity
        }

        skipButton = findViewById(R.id.skip_button)

        skipButton.setOnClickListener{
            // TODO: Go to next activity
        }
    }

    fun infoDialog(){
        val alertDialog: AlertDialog = AlertDialog.Builder(this@Voting).create()
        alertDialog.setTitle("Times up!")
        alertDialog.setMessage("The time you had to vote ran out.")
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show()
    }

    private fun checkWinCondition(){
        var numMafiaAlive = 0;
        var numCivilianAlive = 0;

        for(i in mPlayers.indices){
            val player = mPlayers[i]
            if(player.role() == MAFIA && player.alive()){ // if player is mafia and isAlive
                numMafiaAlive += 1
            } else if((player.role() == CIVILIAN ||
                player.role() == DOCTOR ||
                player.role() == DETECTIVE)
                && player.alive()){
                numCivilianAlive += 1
            }
        }

        if(numMafiaAlive == 0){
            // civilians win
        }else if(numCivilianAlive <= MAFIA){ // 1 civilian and 1 civilian, doctor and detective are civilians
            // mafia wins
        }

    }

    /* Display only alive players */
    private fun initializeAlivePlayers(){
        var y = 0;
        mAlivePlayers = Array<Player> (numAlive) {Player("", 0)}

        for(i in mPlayers.indices){
            if(mPlayers[i].alive()){
                mAlivePlayers[y] = mPlayers[i]
                y++
            }
        }
    }

    companion object{
        const val TAG = "tag"
        const val MAFIA = 1
        const val DOCTOR = 2
        const val DETECTIVE = 3
        const val CIVILIAN = 4
    }
}