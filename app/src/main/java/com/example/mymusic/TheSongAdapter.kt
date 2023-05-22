package com.example.mymusic

import android.content.Context
import android.view.LayoutInflater

import android.widget.ArrayAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView





class TheSongAdapter(context: Context, private val data: ArrayList<Song>) :
    ArrayAdapter<Song>(context, 0, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)

        val song = data[position]

        val titleTextView = itemView.findViewById<TextView>(R.id.title)
        val artistTextView = itemView.findViewById<TextView>(R.id.artist)

        titleTextView.text = song.tittle
        artistTextView.text = song.artist

        return itemView
    }
}
