package com.rybeau.golfapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView

class HoleEntryAdapter(context: Context, private val holes: IntArray) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    class HoleEntryViewHolder(itemView: View) {
        val holeNumber: TextView = itemView.findViewById<TextView>(R.id.holeNumber)
        val parInput: EditText = itemView.findViewById<EditText>(R.id.parInput)
        val scoreInput: EditText = itemView.findViewById<EditText>(R.id.scoreInput)
        val puttsInput: EditText = itemView.findViewById<EditText>(R.id.puttsInput)
    }

    override fun getCount(): Int {
        return holes.size
    }

    override fun getItem(position: Int): Any {
        return holes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val viewHolder: HoleEntryViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.hole_entry_item, parent, false)
            viewHolder = HoleEntryViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as HoleEntryViewHolder
        }
        fillViewHolder(viewHolder, position)
        return view
    }

    private fun fillViewHolder(viewHolder: HoleEntryViewHolder, position: Int) {
        viewHolder.holeNumber.text = holes[position].toString()
        viewHolder.parInput.tag = "par${holes[position]}"
        viewHolder.parInput.filters = arrayOf(HoleEntryFilter(1, 5))
        viewHolder.scoreInput.tag = "score${holes[position]}"
        viewHolder.scoreInput.filters = arrayOf(HoleEntryFilter(1, 20))
        viewHolder.puttsInput.tag = "putts${holes[position]}"
        viewHolder.puttsInput.filters = arrayOf(HoleEntryFilter(1, 20))
    }
}

