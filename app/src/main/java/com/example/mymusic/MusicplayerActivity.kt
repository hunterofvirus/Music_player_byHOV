package com.example.mymusic

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import java.io.IOException

class MusicplayerActivity : AppCompatActivity() {

    private lateinit var tvTime: TextView
    private lateinit var tvDuration : TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvArtist: TextView
    private lateinit var seekBarTime: SeekBar
    private lateinit var seekBarVolume: SeekBar
    private lateinit var btnplay: Button

    var musicPlayer :MediaPlayer?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musicplayer)

        val song =if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            intent.getSerializableExtra ("songs",Song::class.java)


        }else{
            intent.getSerializableExtra("song")as Song?


        }
        tvTitle = findViewById(R.id.tvtitle)
        tvTime = findViewById(R.id.tvTime)
        tvDuration = findViewById(R.id.tvDuration)
        tvArtist = findViewById(R.id.tvArtist)
        seekBarTime = findViewById(R.id.seekBarTime)
        seekBarVolume = findViewById(R.id.seekBarVolume)
        btnplay = findViewById(R.id.btnplay)

        musicPlayer=   MediaPlayer()
        musicPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        tvArtist.text = song?.artist
        tvTitle.text = song!!.artist

        try {
            musicPlayer!!.setDataSource(song.path)
            musicPlayer!!.prepare()

        } catch (

            e:IOException
        ){

            e.printStackTrace()
        }

        musicPlayer!!.isLooping=true
        musicPlayer!!.seekTo(0)
        musicPlayer!!.setVolume(0.5F,0.5F)

        val duration= millisecondstoString(musicPlayer!!.duration)
        tvDuration.text = duration
        seekBarTime.progress=50
        seekBarTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{


            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                tvDuration.text = millisecondstoString(progress)
                val volume =progress / 100f
                musicPlayer!!.setVolume(volume, volume)



            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }
        })
seekBarTime.max=musicPlayer!!.duration
        seekBarTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    musicPlayer!!.seekTo(progress)
                    seekBar.progress=progress
                    }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

        })

        btnplay.setOnClickListener {
            if (musicPlayer!!.isPlaying) {

                musicPlayer!!.pause()
                btnplay.setBackgroundResource(R.drawable.play)
            } else {
                musicPlayer!!.start()
                btnplay.setBackgroundResource(R.drawable.play)

            }
        }
    }

    private fun millisecondstoString(time: Int): String {
        var elapsedTime : String?=""
        val seconds = time/ 1000 % 60
        val minutes = time / 1000 / 60

        elapsedTime ="$minutes:"

        if (seconds < 10) {
            elapsedTime += "0"
        }
        elapsedTime += seconds

        return elapsedTime
    }
    }


