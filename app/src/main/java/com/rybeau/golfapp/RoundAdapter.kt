package com.rybeau.golfapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoundAdapter(private val rounds: Array<Round>)
    : RecyclerView.Adapter<RoundAdapter.RoundViewHolder>()
{
    class RoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById<TextView>(R.id.date)
        val score: TextView = itemView.findViewById<TextView>(R.id.score)
        val averagePutts: TextView = itemView.findViewById<TextView>(R.id.averagePutts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.round_item, parent.findViewById(R.id.roundsView), false)
        return RoundViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RoundViewHolder, position: Int) {
        viewHolder.date.text = rounds[position].date
        viewHolder.score.text = rounds[position].score
        viewHolder.averagePutts.text = rounds[position].averagePutts.toString()
    }

    override fun getItemCount() = rounds.size
}