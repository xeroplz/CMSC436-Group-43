package com.example.mafia43

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import java.io.Serializable


class NightActivity : AppCompatActivity() {
    private lateinit var mPlayers : Array<Player>
    private lateinit var mBundle : Bundle
    private lateinit var mTextView : TextView
    private lateinit var mAudioManager: AudioManager
    private lateinit var mHandler: Handler
    private lateinit var mContinueButton: Button
    private var role = MAFIA
    private var vol = 0.5f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_role_confirm)

        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>
        mTextView = findViewById(R.id.nRoleTextView)
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mHandler = Handler(Looper.getMainLooper())
        mContinueButton = findViewById(R.id.nContinue)

        role =  intent.getIntExtra("Role", MAFIA)
        val alive = intent.getIntExtra("AlivePlayers", mPlayers.size)
        val killPlayer = intent.getStringExtra("Kill")
        val savePlayer = intent.getStringExtra("Save")


        when (role) {
            1 -> {
                for (player in mPlayers) {
                    if(player.role() == MAFIA) {
                        if(player.alive()) {
                            mTextView.text = "Mafia Awake..."
                            mHandler.postDelayed(mRunnable, 3000)
                        } else {
                            /* send to Mafia loss screen */
                        }
                    }
                }
            }
            2 -> {
                for (player in mPlayers) {
                    if(player.role() == DOCTOR) {
                        if(player.alive()) {
                            mTextView.text = "Doctor Awake..."
                            mHandler.postDelayed(mRunnable, 3000)
                        } else {
                            mHandler.postDelayed(mRunnable, 3000)
                            val nightIntent = Intent(this@NightActivity, NightActivity::class.java)
                            /* You have to create a Bundle to pass the Player array */
                            val args = Bundle()
                            args.putSerializable("playersArr", mPlayers as Serializable)
                            nightIntent.putExtra("Bundle", args)
                            nightIntent.putExtra("AlivePlayers", alive)
                            nightIntent.putExtra("Kill", intent.getStringExtra("Kill"))
                            nightIntent.putExtra("Save", intent.getStringExtra("Save"))
                            nightIntent.putExtra("Role", DETECTIVE)
                            startActivity(nightIntent)
                        }
                    }
                }
            }
            3 -> {
                for (player in mPlayers) {
                    if(player.role() == DETECTIVE) {
                        if(player.alive()) {
                            mTextView.text = "Detective Awake..."
                            mHandler.postDelayed(mRunnable, 8000)
                        } else {
                            mHandler.postDelayed(mRunnable, 8000)

                            /* Send to night recap page */
                            val nightIntent = Intent(this@NightActivity, NightRecapActivity::class.java)
                            /* You have to create a Bundle to pass the Player array */
                            val args = Bundle()
                            args.putSerializable("playersArr", mPlayers as Serializable)
                            nightIntent.putExtra("Bundle", args)
                            nightIntent.putExtra("AlivePlayers", intent.getIntExtra("AlivePlayers", mPlayers.size))
                            nightIntent.putExtra("Kill", intent.getStringExtra("Kill"))
                            nightIntent.putExtra("Save", intent.getStringExtra("Save"))
                            startActivity(nightIntent)
                        }
                    }
                }
            }
        }

        mContinueButton.setOnClickListener {
            val nightIntent = Intent(this@NightActivity, NightSelectionActivity::class.java)
            /* You have to create a Bundle to pass the Player array */
            val args = Bundle()
            args.putSerializable("playersArr", mPlayers as Serializable)
            nightIntent.putExtra("Bundle", args)
            nightIntent.putExtra("AlivePlayers", alive)
            nightIntent.putExtra("Role", role)
            nightIntent.putExtra("Kill", killPlayer)
            nightIntent.putExtra("Save", savePlayer)
            startActivity(nightIntent)
        }
    }

    private val mRunnable = Runnable {
        when (role) {
            MAFIA -> mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_INVALID, vol)
            DOCTOR -> mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR, vol)
            DETECTIVE -> mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN, vol)
        }
    }

    companion object{
        const val TAG = "night_activity"
        const val MAFIA = 1
        const val DOCTOR = 2
        const val DETECTIVE = 3
        const val CIVILIAN = 4
    }

}