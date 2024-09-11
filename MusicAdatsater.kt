package com.music.player.presentation.welcomePage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.music.player.R

class MusicAdatsater(
    private val data: List<DataModel>,
    private val applicationContext: Context,
    private val welcomePage: WelcomePage
) :
    RecyclerView.Adapter<MusicAdatsater.ViewHolder>()  {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title = view.findViewById<TextView>(R.id.music_title)
        var duration = view.findViewById<TextView>(R.id.duration)
        var dis = view.findViewById<TextView>(R.id.music_location)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val positiondata=data[position]
        holder.title.text=positiondata.title
        val min=((positiondata.duration/1000)/60).toString()
        val sec = ((positiondata.duration/1000)%60).toString()
        val dur:String=min+"."+sec
        holder.duration.text=dur
        holder.dis.text=positiondata.artist.toString()
        holder.itemView.setOnClickListener {
           welcomePage.movetoMusicplayer(positiondata)
        }
    }


    companion object {
        fun filter(query: String,wholedata:List<DataModel>) {
            wholedata.filter { item ->



                // Implement your filtering logic here. For example, you can check if the item's title contains the query.
                item.title.contains(query, true)

            }


        }
    }

}
