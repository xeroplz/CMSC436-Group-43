package com.example.mafia43

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class NightSelectionActivity : AppCompatActivity() {

    private lateinit var mConfirmButton : Button
    private lateinit var mPlayers : Array<Player>
    private lateinit var currPlayers : Array<Player>
    private lateinit var mBundle : Bundle
    private lateinit var selected : String
    private lateinit var mRoleView : TextView
    private lateinit var mPromptView : TextView
    private lateinit var nightIntent : Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_role_selection)

        /* Get players array from intent Bundle */
        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>
        val role = intent.getIntExtra("Role", 0)
        val alive = intent.getIntExtra("AlivePlayers", mPlayers.size)
        mRoleView = findViewById(R.id.nsTextView2)
        mPromptView = findViewById(R.id.nsTextView3)

        when(role) {
            MAFIA -> {
                mRoleView.setText("MAFIA")
                mPromptView.setText("Who do you want to kill?")
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
                mRoleView.setText("DOCTOR")
                mPromptView.setText("Who do you want to save?")
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
                mRoleView.setText("DETECTIVE")
                mPromptView.setText("Who do you want to check?")
                currPlayers = Array<Player>(alive-1) {Player("", 0)}
                var j = 0
                for(i in 0..mPlayers.size-1) {
                    if (mPlayers[i].alive() && mPlayers[i].role() != DETECTIVE) {
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
        
        selected = ""

        /* position is items position in current players array */
        listView.setOnItemClickListener{ parent: AdapterView<*>, view: View, position: Int, id: Long ->
            Log.i(TAG, currPlayers[position].name())
            selected = currPlayers[position].name()
        }

        /* When button is clicked */
        mConfirmButton = findViewById(R.id.confirm_button)

        mConfirmButton.setOnClickListener{
            if (selected == "") {
                Toast.makeText(this, "Please select a player...", Toast.LENGTH_SHORT).show()
            } else {
                when (role) {
                    MAFIA -> {
                        nightIntent =
                            Intent(this@NightSelectionActivity, NightActivity::class.java)
                        /* You have to create a Bundle to pass the Player array */
                        val args = Bundle()
                        args.putSerializable("playersArr", mPlayers as Serializable)
                        nightIntent.putExtra("Bundle", args)
                        nightIntent.putExtra("AlivePlayers", alive)
                        nightIntent.putExtra("Role", DOCTOR)
                        nightIntent.putExtra("Kill", selected)
                        nightIntent.putExtra("Save", "")
                        displayMsg()
                    }
                    DOCTOR -> {
                        nightIntent =
                            Intent(this@NightSelectionActivity, NightActivity::class.java)
                        /* You have to create a Bundle to pass the Player array */
                        val args = Bundle()
                        args.putSerializable("playersArr", mPlayers as Serializable)
                        nightIntent.putExtra("Bundle", args)
                        nightIntent.putExtra("AlivePlayers", alive)
                        nightIntent.putExtra("Role", DETECTIVE)
                        nightIntent.putExtra("Kill", intent.getStringExtra("Kill"))
                        nightIntent.putExtra("Save", selected)
                        displayMsg()
                    }
                    DETECTIVE -> {
                        nightIntent =
                            Intent(this@NightSelectionActivity, DetectiveActivity::class.java)
                        /* You have to create a Bundle to pass the Player array */
                        val args = Bundle()
                        args.putSerializable("playersArr", mPlayers as Serializable)
                        nightIntent.putExtra("Bundle", args)
                        nightIntent.putExtra("AlivePlayers", alive)
                        nightIntent.putExtra("Kill", intent.getStringExtra("Kill"))
                        nightIntent.putExtra("Save", intent.getStringExtra("Save"))
                        nightIntent.putExtra("Check", selected)
                        startActivity(nightIntent)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@NightSelectionActivity)

        builder.setTitle("Quit Game")
        builder.setMessage("Are you sure you want to end the game?")
        builder.setNegativeButton("Yes", DialogInterface.OnClickListener{ _, _ ->
            val restartIntent = Intent(this@NightSelectionActivity, TitleActivity::class.java)
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

    private fun displayMsg() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@NightSelectionActivity)

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

    companion object{
        const val TAG = "night_selection"
        const val MAFIA = 1
        const val DOCTOR = 2
        const val DETECTIVE = 3
        const val CIVILIAN = 4
    }
}