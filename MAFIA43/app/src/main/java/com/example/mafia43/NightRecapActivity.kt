package com.example.mafia43

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class NightRecapActivity : AppCompatActivity() {

    private lateinit var mContinueButton : Button
    private lateinit var mPlayers : Array<Player>
    private lateinit var mTextView : TextView
    private lateinit var mNightView : TextView
    private lateinit var mHandler: Handler
    private lateinit var mBundle : Bundle
    private lateinit var mAudioManager: AudioManager
    private lateinit var mSoundPool: SoundPool
    private var mSoundId: Int = 0
    private var cont = false

    // Audio volume
    private var mStreamVolume: Float = 0.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.night_role_confirm)

        /* Get players array from intent Bundle */
        mBundle = intent.getBundleExtra("Bundle")!!
        mPlayers = mBundle.getSerializable("playersArr") as Array<Player>
        mHandler = Handler(Looper.getMainLooper())

        mContinueButton = findViewById(R.id.nContinue)
        mNightView = findViewById(R.id.nTextView)
        mTextView = findViewById(R.id.nRoleTextView)
        val killedPlayer = intent.getStringExtra("Kill")

        // Get reference to the AudioManager
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        this.volumeControlStream = AudioManager.STREAM_MUSIC

        for(player in mPlayers) {
            if (player.role() == DETECTIVE) {
                if(player.alive()) {
                    mHandler.postDelayed(mRunnable, 4000)
                } else {
                    mHandler.postDelayed(mRunnable, 20000)
                }
            }
        }

        val rand = (1..22).random()
        mTextView.setTextSize(20f)
        mNightView.setText("Night Recap")

        when (rand) {
            1 -> mTextView.setText("At night, " + killedPlayer + " was reportedly smothered by cloaks and hats showered upon them by an intruder during their sleep.")
            2 -> mTextView.setText("At night, " + killedPlayer + "’s neck was snapped by the mafia due to a disagreement earlier in the day over whether a “hot dog is a sandwich”. ")
            3 -> mTextView.setText("At night, " + killedPlayer + " was being chased by the mafia. They almost outran the mafia, but came across a bean field, which they refused to run across because they had prohibited beans as ritually unclean. Since cutting the beans would violate their beliefs, they decided to simply stop running.")
            4 -> mTextView.setText("At night, the mafia came in while " + killedPlayer + " was sleeping and smeared them with cow manure. The scent attracted their dogs, but not in a good way.")
            5 -> mTextView.setText("At night, the mafia slipped bull blood in " + killedPlayer + "’s milk. " + killedPlayer + " drank the milk and fell ill.")
            6 -> mTextView.setText("At night, the mafia sent an eagle carrying a tortoise to " + killedPlayer + "’s house. " + killedPlayer + ", who was tending to their garden, heard the eagle fly over and looked up to see a tortoise falling towards their head. Normally, eagles drop tortoises on rocks to break their shell, but the bird thought " + killedPlayer + "’s head was a rock in the dark night.")
            7 -> mTextView.setText("At night, the mafia came to " + killedPlayer + "’s house and drugged their night cup of coffee. This drug made " + killedPlayer + " think that they were an immortal god and to prove it to themselves they headed off to the nearest volcano to jump in.")
            8 -> mTextView.setText("At night, the mafia came to pay " + killedPlayer + " a visit. With them, they brought wine and some grapes from their orchid. " + killedPlayer + " started to eat the grape, but it turned out it was unripe. " + killedPlayer + " started to choke on the food.")
            9 -> mTextView.setText("At night, the mafia came to " + killedPlayer + "’s house. They restrained and slatthered " + killedPlayer + " in milk and honey, while they were sleeping in an attempt to kill them by scaphism (I recommend not searching this up).")
            10 -> mTextView.setText("At night, the mafia threw a pear at " + killedPlayer + ".")
            11 -> mTextView.setText("At night, the mafia sent a package to " + killedPlayer + "’s house. The package contained mercury pills that were said to grant them eternal life.")
            12 -> mTextView.setText("At night, " + killedPlayer + " noticed that their pet donkey had gotten out of their pen. The donkey went up to " + killedPlayer + "’s fig tree and started eating a fig. This humored " + killedPlayer + " and made them start laughing like crazy.")
            13 -> mTextView.setText("At night, the mafia came and forced " + killedPlayer + " to swallow hot coals while they were sleeping.")
            14 -> mTextView.setText("At night, the mafia came and took " + killedPlayer + " out of their house. They ended up restraining them and started roasting them alive.")
            15 -> mTextView.setText("At night, the mafia came and took " + killedPlayer + ". The mafia threw " + killedPlayer + " into a pit of snakes.")
            16 -> mTextView.setText("At night, " + killedPlayer + " was stabbed whilst on the toilet by the mafia, who were hiding underneath.")
            17 -> mTextView.setText("At night, " + killedPlayer + " went to sit on their new IKEA chair, but it turns out that the chair was missing a leg because the mafia owns IKEA.")
            18 -> mTextView.setText("At night, " + killedPlayer + " was rolled up in a rug by the mafia. The mafia then sent horses to trample over them.")
            19 -> mTextView.setText("At night, " + killedPlayer + " was sleeping in a linen sheet. The mafia came and poured distilled spirits on the linen sheet and sent the highly flammable sheet on fire.")
            20 -> mTextView.setText("At night, the mafia came and took " + killedPlayer + ". The mafia put " + killedPlayer + " in a barrel of Malmsey wine.")
            21 -> mTextView.setText("At night, the mafia came to " + killedPlayer + "’s house and started to play their favorite song. This made " + killedPlayer + " start dancing not realizing it was on a ten hour loop.")
            22 -> mTextView.setText("At night, " + killedPlayer + " tripped over their own beard.")
        }

        mContinueButton.setOnClickListener {
            if(cont) {
                val nightIntent = Intent(this@NightRecapActivity, NightSaveActivity::class.java)

                /* You have to create a Bundle to pass the Player array */
                val args = Bundle()
                args.putSerializable("playersArr", mPlayers as Serializable)
                nightIntent.putExtra("Bundle", args)
                nightIntent.putExtra(
                    "AlivePlayers",
                    intent.getIntExtra("AlivePlayers", mPlayers.size)
                )
                nightIntent.putExtra("Kill", intent.getStringExtra("Kill"))
                nightIntent.putExtra("Random", rand)

                if (killedPlayer == intent.getStringExtra("Save")) {
                    nightIntent.putExtra("Saved", true)
                    startActivity(nightIntent)
                } else {
                    nightIntent.putExtra("Saved", false)
                }
                
                startActivity(nightIntent)
            }
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

        mSoundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            mSoundPool.play(mSoundId, mStreamVolume, mStreamVolume, 1, 0, 1f)
            cont = true
        }

        mSoundId = mSoundPool.load(this@NightRecapActivity, R.raw.night_recap, 1)
    }

    companion object{
        const val TAG = "night_recap_activity"
        const val MAFIA = 1
        const val DOCTOR = 2
        const val DETECTIVE = 3
        const val CIVILIAN = 4
    }
}