package com.example.mymusic

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.AdapterView
import android.widget.ListView
import androidx.core.app.ActivityCompat
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var songListView:ListView
    var songsArrayList = ArrayList<Song>()
    var songsAdapter:TheSongAdapter? = null








    companion object {

        private const val REQUEST_PERMISSION = 99
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songListView = findViewById(R.id.songListView)

        songsAdapter=TheSongAdapter(this,songsArrayList)

        songListView.adapter= songsAdapter

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {


            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_PERMISSION)
            return

            }

            else{

                getSong()
            }


            songListView.onItemClickListener = AdapterView.OnItemClickListener() { parent, view, position, id ->
                Intent(this@MainActivity,MusicplayerActivity::class.java).also{

                    startActivity(intent)

                }
            }

        // MainActivity.kt
// ...
        songListView.setOnItemClickListener { parent, view, position, id ->
            val song = songsArrayList[position]
            val intent = Intent(this, MusicplayerActivity::class.java)
            intent.putExtra("song", song)
            startActivity(intent)
        }
// ...



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSong()
            }
        }
    }

    private fun getSong() {

        val songCursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null)
        if(songCursor!=null && songCursor.moveToFirst()) {
        val indexTitle =songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val indexArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val indexData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val indexIsMusic = songCursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)

        do {
            val title = songCursor.getString(indexTitle)
            var artist = songCursor.getString(indexArtist)
            val isMusic = songCursor.getInt(indexIsMusic)
            val path= songCursor.getString(indexData)

            if(artist=="<unknown"){
                artist="unknown"
            }


            if(isMusic == 1 ) {

                songsArrayList.add(Song(title, artist, path ))

            }

        } while (songCursor.moveToNext())
        songCursor.close()



        }

        songsAdapter?.notifyDataSetChanged()
    }


}

