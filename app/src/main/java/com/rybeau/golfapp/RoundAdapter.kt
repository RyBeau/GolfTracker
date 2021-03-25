package com.rybeau.golfapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoundAdapter(private var rounds: List<Round>, private val onRoundListener: OnRoundListener)
    : RecyclerView.Adapter<RoundAdapter.RoundViewHolder>()
{
    interface OnRoundListener{
        fun onRoundClick(position: Int)
    }

    class RoundViewHolder(itemView: View, val onRoundListener: OnRoundListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val date: TextView = itemView.findViewById<TextView>(R.id.date)
        val score: TextView = itemView.findViewById<TextView>(R.id.score)
        val averagePutts: TextView = itemView.findViewById<TextView>(R.id.averagePutts)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onRoundListener.onRoundClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.round_item, parent.findViewById(R.id.roundsView), false)
        return RoundViewHolder(view, onRoundListener)
    }

    override fun onBindViewHolder(viewHolder: RoundViewHolder, position: Int) {
        viewHolder.date.text = rounds[position].date
        viewHolder.score.text = rounds[position].score.toString()
        viewHolder.averagePutts.text = rounds[position].averagePutts.toString()
    }

    override fun getItemCount() = rounds.size

    fun setData(newRounds: List<Round>){
        rounds = newRounds
    }
}