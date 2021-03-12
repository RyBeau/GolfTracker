package com.rybeau.golfapp

import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HoleEntryAdapter(private val holes: IntArray)
    : RecyclerView.Adapter<HoleEntryAdapter.HoleEntryViewHolder>()
{
    class HoleEntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val holeNumber: TextView = itemView.findViewById<TextView>(R.id.holeNumber)
        val parInput: EditText = itemView.findViewById<EditText>(R.id.parInput)
        val scoreInput: EditText = itemView.findViewById<EditText>(R.id.scoreInput)
        val puttsInput: EditText = itemView.findViewById<EditText>(R.id.puttsInput)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoleEntryViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.hole_entry_item, parent.findViewById(R.id.inputView), false)
        return HoleEntryViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: HoleEntryViewHolder, position: Int) {
        viewHolder.holeNumber.text = holes[position].toString()
        viewHolder.parInput.tag = "par${holes[position]}"
        viewHolder.parInput.filters = arrayOf(HoleEntryFilter(1, 5))
        viewHolder.scoreInput.tag = "score${holes[position]}"
        viewHolder.scoreInput.filters = arrayOf(HoleEntryFilter(1, 20))
        viewHolder.puttsInput.tag = "putts${holes[position]}"
        viewHolder.puttsInput.filters = arrayOf(HoleEntryFilter(1, 20))
    }

    override fun getItemCount() = holes.size
}