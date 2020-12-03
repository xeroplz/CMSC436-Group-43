package com.example.mafia43

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class Voting : AppCompatActivity() {
    lateinit var extras : Bundle
    lateinit var mInflater: LayoutInflater
    lateinit var inflatedList : ListView
    lateinit var confirmButton : Button
    lateinit var mPlayers : Array<Player>
    lateinit var mBundle : Bundle
    lateinit var votedOff : Player
    var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.voting_selection)

        /* Get players array from intent Bundle */
        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>

        /* Instantiate listView and Adapter */

        /* If you want to recreate this, just create a ListView in the layout resource file,
         and use your listView instead of "player_list" */

        val listView = findViewById<ListView>(R.id.player_list)
        listView.adapter = PlayerListAdapter(
            this,
            R.layout.player_list_item,
            mPlayers
        )

        listView.setBackgroundColor(resources.getColor(R.color.white, null))

        /* position is items position in players array */
        listView.setOnItemClickListener{parent: AdapterView<*>, view: View, position: Int, id: Long ->

            Log.i(TAG, mPlayers[position].name())
            votedOff = mPlayers[position]

        }

        /* When button is clicked, set person voted off to die()
        *  TODO: Maybe I should exclude mafia in voting off list
        * */
        confirmButton = findViewById(R.id.confirm_button)

        confirmButton.setOnClickListener{
            votedOff.die()
            checkWinCondition()
        }

    }

    fun checkWinCondition(){
        var numMafiaAlive = 0;
        var numCivilianAlive = 0;

        for(i in mPlayers.indices){
            val player = mPlayers[i]
            if(player.role() == MAFIA && player.alive()){ // if player is mafia and isAlive
                numMafiaAlive += 1
            } else if(player.role() == CIVILIAN && player.alive()){
                numCivilianAlive += 1
            }
        }

        if(numMafiaAlive == 0){
            // civilians win
        }else if(numCivilianAlive <= MAFIA){
            // mafia wins
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