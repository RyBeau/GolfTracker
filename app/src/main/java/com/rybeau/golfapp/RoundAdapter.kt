package com.rybeau.golfapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Custom Adapter for showing previous rounds.
 */
class RoundAdapter(private var rounds: List<Round>, private val onRoundListener: OnRoundListener)
    : RecyclerView.Adapter<RoundAdapter.RoundViewHolder>()
{
    interface OnRoundListener{
        fun onRoundClick(position: Int)
    }

    class RoundViewHolder(itemView: View, val onRoundListener: OnRoundListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val date: TextView = itemView.findViewById(R.id.date)
        val score: TextView = itemView.findViewById(R.id.score)
        val averagePutts: TextView = itemView.findViewById(R.id.averagePutts)

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
        viewHolder.averagePutts.text = String.format("%.1f", rounds[position].averagePutts)
    }

    override fun getItemCount() = rounds.size

    fun setData(newRounds: List<Round>){
        rounds = newRounds
        notifyDataSetChanged()
    }
}