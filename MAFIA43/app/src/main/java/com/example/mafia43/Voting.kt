package com.example.mafia43

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

// TODO: Send voting to DayRecap Activity, SavedInstanceState, disable Back Button, disable confirm button until someone is selected
class Voting : AppCompatActivity() {
    lateinit var mTimerText : TextView
    lateinit var confirmButton : Button
    lateinit var skipButton : Button
    lateinit var mPlayers : Array<Player>
    lateinit var mBundle : Bundle
    var defaultPlayer = Player("No one", -1)
    var votedOff : Player = defaultPlayer
    var numAlive : Int = 0
    lateinit var mAlivePlayers : Array<Player>

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

        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimerText.text = (millisUntilFinished / 1000).toString()
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {

                mTimerText.text = "Time is up!"
                infoDialog()
            }
        }.start()

        /* Instantiate listView and Adapter

        If you want to recreate this, just create a ListView in the layout resource file,
         and use your listView instead of "player_list" */

        val listView = findViewById<ListView>(R.id.player_list)
        listView.adapter = PlayerListAdapter(
            this,
            R.layout.player_list_item_dark,
            mAlivePlayers,
            true
        )

        listView.setBackgroundColor(resources.getColor(R.color.white, null))


        /* position is items position in players array */
        listView.setOnItemClickListener{ _: AdapterView<*>, view : View, position: Int, _: Long ->
            view.isSelected = true;
            Log.i(TAG, mAlivePlayers[position].name())
            votedOff = mAlivePlayers[position]

        }

        /* When button is clicked, set person voted off to die() */
        confirmButton = findViewById(R.id.confirm_button)

        confirmButton.setOnClickListener{

            if(votedOff.name() != defaultPlayer.name()) {
                // Kill off player in players object arrays
                for (i in mPlayers.indices) {
                    if (mPlayers[i].name() == votedOff.name()) {
                        mPlayers[i].die()
                    }
                }

                numAlive -= 1
                checkWinCondition() // Goes to next activity
            }else{
                Log.i("votedOff", votedOff.name())
                Toast.makeText(this, "Select someone to vote off.", Toast.LENGTH_SHORT).show()
            }
        }

        skipButton = findViewById(R.id.skip_button)

        skipButton.setOnClickListener{
            goToVotingRecap("No one")
        }
    }

    override fun onBackPressed() {
        //don't do anything?
    }

    fun infoDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@Voting)

        builder.setTitle("Times up!")
        builder.setMessage("The time you had to vote ran out.")
        builder.setCancelable(false);
        builder.setPositiveButton("OK", DialogInterface.OnClickListener{ _,_ ->
            // go to VoteRecap Activity
            goToVotingRecap("No one")

        })

        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    private fun goToVotingRecap(votedOffString : String){
        val recapIntent = Intent(this@Voting, VoteRecapActivity::class.java)
        val args = Bundle()

        args.putSerializable("playersArr", this.mPlayers)

        recapIntent.putExtra("Bundle", args)
        recapIntent.putExtra("AlivePlayers", numAlive)
        recapIntent.putExtra("VotedOff", votedOffString)

        startActivity(recapIntent)
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

  //      TODO: change this to check for win condition
//        goToVotingRecap(votedOff.name())
        when {
            numMafiaAlive == 0 -> {
                /* civilians win */
                Log.i("winCondition", "civilians win")
            }
            numCivilianAlive <= MAFIA -> { // 1 civilian and 1 civilian, doctor and detective are civilians
                // mafia wins
                Log.i("winCondition", "mafia wins")
            }
            else -> {
                goToVotingRecap(votedOff.name())
            }
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