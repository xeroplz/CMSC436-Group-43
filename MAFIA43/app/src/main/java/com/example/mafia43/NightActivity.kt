package com.example.mafia43

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import java.io.Serializable
import java.util.concurrent.Semaphore


class NightActivity : AppCompatActivity() {
    private lateinit var mPlayers : Array<Player>
    private lateinit var mBundle : Bundle
    private lateinit var mTextView : TextView
    private lateinit var mHandler: Handler
    private lateinit var mContinueButton: Button
    private lateinit var mAudioManager: AudioManager
    private lateinit var mSoundPool: SoundPool
    private var mSoundId: Int = 0
    private var role = MAFIA

    // Audio volume
    private var mStreamVolume: Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_role_confirm)

        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>
        mTextView = findViewById(R.id.nRoleTextView)
        mHandler = Handler(Looper.getMainLooper())
        mContinueButton = findViewById(R.id.nContinue)

        // Get reference to the AudioManager
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        this.volumeControlStream = AudioManager.STREAM_MUSIC

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
                            mHandler.postDelayed(mRunnable, 1000)
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
                        for(player in mPlayers) {
                            if (player.role() == NightRecapActivity.DOCTOR) {
                                if(player.alive()) {
                                    mHandler.postDelayed(mRunnable, 4000)
                                } else {
                                    mHandler.postDelayed(mRunnable, 10000)
                                }
                            }
                        }
                        if(player.alive()) {
                            mTextView.text = "Detective Awake..."
                        } else {
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
        mStreamVolume = mAudioManager!!
            .getStreamVolume(AudioManager.STREAM_MUSIC).toFloat() / mAudioManager!!.getStreamMaxVolume(
            AudioManager.STREAM_MUSIC
        )

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        mSoundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(audioAttributes)
            .build()

        mSoundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->  mSoundPool.play(mSoundId, mStreamVolume, mStreamVolume, 1, 0, 1f) }

        when (role) {
            MAFIA -> {
                mSoundId = mSoundPool.load(this@NightActivity, R.raw.mafia, 1)
            }
            DOCTOR -> {
                mSoundId = mSoundPool.load(this@NightActivity, R.raw.doctor, 1)
            }
            DETECTIVE -> {
                mSoundId = mSoundPool.load(this@NightActivity, R.raw.detective, 1)
            }
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