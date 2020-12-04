package com.example.mafia43

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RoleAssignmentActivity : AppCompatActivity() {
    companion object {
        private val LIST_KEY = "list"
    }

    private lateinit var mTextBox : EditText
    private lateinit var mOkButton : Button
    private lateinit var playerNameList : ArrayList<String>
    private lateinit var mListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_assignment)

        mTextBox = findViewById(R.id.ar_text_box)
        mOkButton = findViewById(R.id.ar_ok_button)

        // Set list values from nothing or a saved instance state
        if (savedInstanceState != null) {
            savedInstanceState?.let { playerNameList = it.getStringArrayList(LIST_KEY)!! }
        } else {
            playerNameList = ArrayList<String>()
        }

        // Show list stuff on the screen
        mListView = findViewById(R.id.ar_player_list)
        updateList()

        mListView.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark, null))

        // Add names to list
        mOkButton.setOnClickListener {
            // Only do something if the name field isn't empty
            val currentText = mTextBox.text.toString()
            Log.i("mafiadebug", "Current Text: $currentText")
            if (currentText != "") {
                // Check for duplicate name
                if (!playerNameList!!.contains(currentText)) {
                    playerNameList.add(currentText)
                    updateList()
                    Toast.makeText(this, "$currentText added to the list.", Toast.LENGTH_SHORT).show()
                } else {
                    // Show toast that the player exists already
                    Toast.makeText(this, "You already have a player with this name.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putStringArrayList(LIST_KEY, playerNameList)
    }

    public override fun onPause() {
        super.onPause()
    }

    public override fun onStart() {
        super.onStart()
    }

    public override fun onResume() {
        super.onResume()
        updateList()
    }

    private fun updateList() {
        mListView!!.adapter = PlayerListAdapter(
            this,
            R.layout.player_list_item,
            getFakePlayerArray()
        )
    }

    private fun getFakePlayerArray() : Array<Player> {
        // Gonna do a quick HACK here since our player list adapter is only for arrays
        var fakePlayerArrayList = ArrayList<Player>()
        for (name in playerNameList!!) {
            fakePlayerArrayList.add(Player(name, 1))
        }
        return fakePlayerArrayList.toTypedArray()
    }

    // Override onBackPressed to remove last-entered player's name from the list

}