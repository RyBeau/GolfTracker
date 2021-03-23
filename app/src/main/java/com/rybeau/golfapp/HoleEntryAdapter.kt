package com.rybeau.golfapp

import android.content.Context
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged

class HoleEntryAdapter(context: Context, holes: Int) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val parValues: MutableList<String?> = MutableList<String?>(holes){null}
    private val scoreValues: MutableList<String?> = MutableList<String?>(holes){null}
    private val puttValues: MutableList<String?> = MutableList<String?>(holes){null}

    class HoleEntryViewHolder(itemView: View) {
        val holeNumber: TextView = itemView.findViewById<TextView>(R.id.holeNumber)
        val parInput: EditText = itemView.findViewById<EditText>(R.id.parInput)
        val scoreInput: EditText = itemView.findViewById<EditText>(R.id.scoreInput)
        val puttsInput: EditText = itemView.findViewById<EditText>(R.id.puttsInput)
    }

    override fun getCount(): Int {
        return parValues.size
    }

    override fun getItem(position: Int): Any {
        return List<String?>(3){parValues[position]; scoreValues[position]; puttValues[position]}
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return position
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

    fun getParValues(): List<String?> {
        return parValues
    }

    fun getScoreValues(): List<String?> {
        return scoreValues
    }

    fun getPuttsValues(): List<String?> {
        return puttValues
    }

    private fun fillViewHolder(viewHolder: HoleEntryViewHolder, position: Int) {
        viewHolder.holeNumber.text = (position + 1).toString()
        if (parValues[position] != null){
            viewHolder.parInput.setText(parValues[position].toString())
        }
        viewHolder.parInput.filters = arrayOf(HoleEntryFilter(1, 5))
        viewHolder.parInput.doAfterTextChanged {
            val text = viewHolder.parInput.text.toString()
            if (parValues[position] != text){
                parValues[position] = text
            } else if (text == ""){
                parValues[position] = null
            }
        }
        if (scoreValues[position] != null){
            viewHolder.scoreInput.setText(scoreValues[position].toString())
        }
        viewHolder.scoreInput.filters = arrayOf(HoleEntryFilter(1, 20))
        viewHolder.scoreInput.doAfterTextChanged {
            val text = viewHolder.scoreInput.text.toString()
            if (scoreValues[position] != text){
                scoreValues[position] = text
            } else if (text == ""){
                scoreValues[position] = null
            }
        }
        if (puttValues[position] != null){
            viewHolder.puttsInput.setText(puttValues[position].toString())
        }
        viewHolder.puttsInput.filters = arrayOf(HoleEntryFilter(1, 20))
        viewHolder.puttsInput.doAfterTextChanged {
            val text = viewHolder.puttsInput.text.toString()
            if (puttValues[position] != text){
                puttValues[position] = text
            } else if (text == ""){
                puttValues[position] = null
            }
        }
    }
}

