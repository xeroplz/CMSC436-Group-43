package com.example.mafia43

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class EndActivity : AppCompatActivity() {

    private lateinit var mNewButton: Button
    private lateinit var mSameButton: Button
    private lateinit var mBundle : Bundle
    private lateinit var mPlayers : Array<Player>
    private lateinit var mReturnButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>

        val mafiaWin = intent.getBooleanExtra("Mafia", false)
        val playerNameList = ArrayList<String>()
        for(player in mPlayers) { playerNameList.add(player.name()) }

        if (mafiaWin) {
            setContentView(R.layout.mafia_win)
        } else {
            setContentView(R.layout.civilian_win)
        }

        mSameButton = findViewById(R.id.button)
        mNewButton = findViewById(R.id.button2)
        mReturnButton = findViewById(R.id.titleButton)

        mSameButton.setOnClickListener {
            // Add in some functionality to reassign the roles

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
            for (name in copiedPlayerNameList) { realPlayerArrayList.add(Player(name, CIVILIAN))}

            // Debug
            for (player in realPlayerArrayList) {
                Log.i(TAG, "Player: ${player.name()}, Role: ${player.roleString()}, Alive: ${player.alive()}")
            }

            // Get true Player array with  roles assigned
            val playerArray = realPlayerArrayList.shuffled().toTypedArray()

            // Start the activity that tells people their roles.
            val roleViewIntent = Intent(this@EndActivity, RoleViewActivity::class.java)
            roleViewIntent.putExtra("PlayerArray", playerArray)
            startActivity(roleViewIntent)
        }

        mNewButton.setOnClickListener {
            // Start the character creation activity!
            val intent = Intent(this@EndActivity, RoleAssignmentActivity::class.java)
            startActivity(intent)
        }

        mReturnButton.setOnClickListener {
            // Start the title screen activity!
            val intent = Intent(this@EndActivity, TitleActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    companion object{
        const val TAG = "end_activity"
        const val MAFIA = 1
        const val DOCTOR = 2
        const val DETECTIVE = 3
        const val CIVILIAN = 4
    }
}