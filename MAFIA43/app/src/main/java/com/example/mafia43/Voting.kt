package com.example.mafia43

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView.OnItemClickListener
import kotlinx.android.synthetic.main.voting_selection.*


class Voting : AppCompatActivity() {
    lateinit var extras : Bundle
    lateinit var mInflater: LayoutInflater
    lateinit var inflatedList : ListView
    lateinit var votingView : TextView
    lateinit var mPlayers : Array<String>
    var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.voting_selection)

        /* Get players array from intent */
        mPlayers = intent.getStringArrayExtra("playersArr") as Array<String>

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
        
    }

    companion object{
        const val TAG = "tag"
    }
}