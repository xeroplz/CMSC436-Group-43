package com.example.mafia43

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable
import kotlin.random.Random

class RoleAssignmentActivity : AppCompatActivity() {
    companion object {
        private val LIST_KEY = "list"
        private val TAG = "mafiadebug"
    }

    private lateinit var mTextBox: EditText
    private lateinit var mOkButton: Button
    private lateinit var playerNameList: ArrayList<String>
    private lateinit var mListView: ListView
    private lateinit var mStartButton: Button
    private val playersMin = 5
    private val playersMax = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_assignment)

        mTextBox = findViewById(R.id.ar_text_box)
        mOkButton = findViewById(R.id.ar_ok_button)
        mStartButton = findViewById(R.id.ar_continue_button)

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

            if (currentText != "") {
                // Check for duplicate name
                if (!playerNameList.contains(currentText)) {
                    // Size Check
                    val size = playerNameList.size
                    if (size <= playersMax) {

                        playerNameList.add(currentText)
                        updateList()
                        mTextBox.text.clear()

                        // Show toast that the player has been added.
                        Toast.makeText(this, "$currentText added to the list.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Show toast that there are too many players.
                        Toast.makeText(this, "Only a maximum of $playersMax players is allowed.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Show toast that the player exists already
                    Toast.makeText(this, "You already have a player with this name.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Create roles for people
        mStartButton.setOnClickListener {
            // Min players 5
            val size = playerNameList.size
            if (size >= playersMin) {
                // Get true Player array with  roles assigned
                val playerArray = getRealPlayerArray()

                // Start the activity that tells people their roles.
                val voteIntent = Intent(this@RoleAssignmentActivity, RoleViewActivity::class.java)
                voteIntent.putExtra("PlayerArray", playerArray)
                startActivity(voteIntent)
            } else {
                // Show toast that there are too little players.
                Toast.makeText(this, "A minimum of $playersMin players is required.", Toast.LENGTH_SHORT).show()
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

    private fun getFakePlayerArray(): Array<Player> {
        // Gonna do a quick HACK here since our player list adapter is only for arrays
        var fakePlayerArrayList = ArrayList<Player>()
        for (name in playerNameList!!) {
            fakePlayerArrayList.add(Player(name, 1))
        }
        return fakePlayerArrayList.toTypedArray()
    }

    private fun getRealPlayerArray(): Array<Player> {
        // 1 mafia, 1 doctor 1 detective, rest civilians
        // take 3 randoms out of the player list and assign them role 1, 2, and 3
        // take the rest of the player list and assign role 4
        var realPlayerArrayList = ArrayList<Player>()

        // Copy name to new list so we can edit it without worries
        var copiedPlayerNameList = ArrayList<String>()
        for (name in playerNameList!!) { copiedPlayerNameList.add(name) }

        // Assign Mafia, Doctor, and Detective
        for (x in 1..3) {
            val size = copiedPlayerNameList.size
            val index = Random.nextInt(0, size)
            realPlayerArrayList.add(Player(copiedPlayerNameList.get(index), x))
            copiedPlayerNameList.removeAt(index)
        }

        // Assign Civilians
        for (name in copiedPlayerNameList) { realPlayerArrayList.add(Player(name, 4))}

        // Debug
        for (player in realPlayerArrayList) {
            Log.i(TAG, "Player: ${player.name()}, Role: ${player.roleString()}, Alive: ${player.alive()}")
        }

        return realPlayerArrayList.toTypedArray()
    }

    // Remove last person from lest when back is pressed
    public override fun onBackPressed() {
        //super.onBackPressed()
        val size = playerNameList!!.size
        if (size >= 1) {
            playerNameList.removeAt(size - 1)
            updateList()
        }
    }
}