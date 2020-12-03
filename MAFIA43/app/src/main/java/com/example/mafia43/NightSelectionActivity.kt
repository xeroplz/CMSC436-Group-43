package com.example.mafia43

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class NightSelectionActivity : AppCompatActivity() {

    lateinit var mConfirmButton : Button
    lateinit var mPlayers : Array<Player>
    lateinit var currPlayers : Array<Player>
    lateinit var mBundle : Bundle
    lateinit var selected : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_role_selection)

        /* Get players array from intent Bundle */
        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>
        val role = intent.getIntExtra("Role", 0)
        val alive = intent.getIntExtra("AlivePlayers", mPlayers.size)

        when(role) {
            MAFIA -> {
                currPlayers = Array<Player>(alive - 1) {Player("", 0)}
                var j = 0
                for(i in 0..mPlayers.size-1) {
                    if (mPlayers[i].alive() && mPlayers[i].role() != MAFIA) {
                        currPlayers[j] = mPlayers[i]
                        j++
                    }
                }
            }
            DOCTOR -> {
                currPlayers = Array<Player>(alive) {Player("", 0)}
                var j = 0
                for(i in 0..mPlayers.size-1) {
                    if (mPlayers[i].alive()) {
                        currPlayers[j] = mPlayers[i]
                        j++
                    }
                }
            }
            DETECTIVE -> {
                currPlayers = Array<Player>(alive) {Player("", 0)}
                var j = 0
                for(i in 0..mPlayers.size-1) {
                    if (mPlayers[i].alive()) {
                        currPlayers[j] = mPlayers[i]
                        j++
                    }
                }
            }
        }

        /* Instantiate listView and Adapter */


        /* If you want to recreate this, just create a ListView in the layout resource file,
         and use your listView instead of "player_list" */

        val listView = findViewById<ListView>(R.id.player_list)
        listView.adapter = PlayerListAdapter(
            this,
            R.layout.player_list_item,
            currPlayers
        )

        listView.setBackgroundColor(resources.getColor(R.color.white, null))

        /* position is items position in current players array */
        listView.setOnItemClickListener{ parent: AdapterView<*>, view: View, position: Int, id: Long ->
            Log.i(TAG, currPlayers[position].name())
            selected = currPlayers[position].name()
        }

        /* When button is clicked */
        mConfirmButton = findViewById(R.id.confirm_button)

        mConfirmButton.setOnClickListener{

        }
    }

    companion object{
        const val TAG = "night_selection"
        const val MAFIA = 1
        const val DOCTOR = 2
        const val DETECTIVE = 3
        const val CIVILIAN = 4
    }
}