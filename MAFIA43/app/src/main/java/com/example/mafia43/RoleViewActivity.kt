package com.example.mafia43

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class RoleViewActivity: AppCompatActivity() {
    companion object {
        private val TAG = "mafiadebug"
    }

    private lateinit var mNameText: TextView
    private lateinit var mRoleText: TextView
    private lateinit var mContinueButton: Button
    private var counter = 1
    private var currPlayer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_view)

        mNameText = findViewById(R.id.rv_name)
        mRoleText = findViewById(R.id.rv_role)
        mContinueButton = findViewById(R.id.rv_continue_button)

        var playerArray = intent.getSerializableExtra("PlayerArray") as Array<Player>

        // Initial display
        val player1 = playerArray.get(0)
        mNameText.text = "Please pass the device to ${player1.name()}."
        mRoleText.text = ""
        mRoleText.visibility = View.INVISIBLE
        mContinueButton.text = "OK"

        val size = playerArray.size
        mContinueButton.setOnClickListener {
            // Start next activity if current player is oob
            if (currPlayer >= size) {
                // TODO: start next activity
                // This is a temporary measure cause idk what half of these extra values are for...
                // Are we going to switch everything up to use standard putExtra array passing?
                val nightIntent = Intent(this@RoleViewActivity, NightActivity::class.java)
                val args = Bundle()
                args.putSerializable("playersArr", playerArray as Serializable)
                nightIntent.putExtra("Bundle", args)
                nightIntent.putExtra("AlivePlayers", size)
                nightIntent.putExtra("Role", 1)
                nightIntent.putExtra("Kill", "")
                nightIntent.putExtra("Save","")
                startActivity(nightIntent)
            } else {
                val player = playerArray.get(currPlayer)
                // Even number = "Please pass the phone to [player]"
                if (counter % 2 == 0) {
                    mNameText.text = "Please pass the device to ${player.name()}."
                    mRoleText.text = ""
                    mRoleText.visibility = View.INVISIBLE
                    mContinueButton.text = "OK"
                    counter++
                } else {
                    // Odd counter = "[Player], your role is [role]."
                    mRoleText.text = "${player.name()}, your role is ${player.roleString()}."
                    mRoleText.visibility = View.VISIBLE
                    mContinueButton.text = "CONTINUE"
                    counter++
                    currPlayer++
                }
            }
        }
    }

    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@RoleViewActivity)

        builder.setTitle("Quit Game")
        builder.setMessage("Are you sure you want to end the game?")
        builder.setNegativeButton("Yes", DialogInterface.OnClickListener{ _, _ ->
            val restartIntent = Intent(this@RoleViewActivity, TitleActivity::class.java)
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
}