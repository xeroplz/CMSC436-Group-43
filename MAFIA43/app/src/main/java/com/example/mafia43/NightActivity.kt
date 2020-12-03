package com.example.mafia43

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import java.io.Serializable


class NightActivity : AppCompatActivity() {
    private lateinit var mPlayers : Array<Player>
    private lateinit var mBundle : Bundle
    private lateinit var mTextView : TextView
    private lateinit var mAudioManager: AudioManager
    private lateinit var mHandler: Handler
    private var role = 1
    private var vol = 0.5f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_role_confirm)

        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>
        mTextView = findViewById(R.id.nRoleTextView)
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mHandler = Handler(Looper.getMainLooper())

        role =  intent.getIntExtra("Role", 1)
        val alive = intent.getIntExtra("AlivePlayers", mPlayers.size)


        when (role) {
            1 -> {
                for (player in mPlayers) {
                    if(player.role() == 1) {
                        if(player.alive()) {
                            mTextView.text = "Mafia Awake"
                            mHandler.postDelayed(mRunnable, 3000)
                        } else {
                            /* send to Mafia loss screen */
                        }
                    }
                }
            }
            2 -> {
                for (player in mPlayers) {
                    if(player.role() == 2) {
                        if(player.alive()) {
                            mTextView.text = "Doctor Awake"
                            mHandler.postDelayed(mRunnable, 3000)
                        } else {
                            val nightIntent = Intent(this@NightActivity, NightActivity::class.java)
                            /* You have to create a Bundle to pass the Player array */
                            val args = Bundle()
                            args.putSerializable("playersArr", mPlayers as Serializable)
                            nightIntent.putExtra("Bundle", args)
                            nightIntent.putExtra("AlivePlayers", alive)
                            nightIntent.putExtra("Role", 3)
                            startActivity(nightIntent)
                        }
                    }
                }
            }
            3 -> {
                for (player in mPlayers) {
                    if(player.role() == 3) {
                        if(player.alive()) {
                            mTextView.text = "Detective Awake"
                            mHandler.postDelayed(mRunnable, 3000)
                        } else {
                            /* send to night recap page */
                        }
                    }
                }
            }
        }

    }

    private val mRunnable = Runnable {
        when (role) {
            1 -> mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_INVALID, vol)
            2 -> mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR, vol)
            3 -> mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN, vol)
        }
    }

}