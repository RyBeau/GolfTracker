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

/**
 * Custom Adapter for Entering Hole Scores, Pars, and Putts
 * Can be initialised with some list of par, score and putt values to persist data between
 * orientation changes.
 */
class HoleEntryAdapter(context: Context, holes: Int, existingParValues: MutableList<String?> = MutableList(holes){null},
                       existingScoreValues: MutableList<String?> = MutableList(holes){null},
                       existingPuttValues: MutableList<String?> = MutableList(holes){null}) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    /**
     * List of Par values linked to the EditText widgets
     */
    private val parValues: MutableList<String?> = existingParValues
    /**
     * List of Score values linked to the EditText widgets
     */
    private val scoreValues: MutableList<String?> = existingScoreValues
    /**
     * List of Putts values linked to the EditText widgets
     */
    private val puttValues: MutableList<String?> = existingPuttValues

    class HoleEntryViewHolder(itemView: View) {
        val holeNumber: TextView = itemView.findViewById(R.id.holeNumber)
        val parInput: EditText = itemView.findViewById(R.id.parInput)
        val scoreInput: EditText = itemView.findViewById(R.id.scoreInput)
        val puttsInput: EditText = itemView.findViewById(R.id.puttsInput)
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

    fun getPuttValues(): List<String?> {
        return puttValues
    }

    /**
     * Configures each Edit Text Widget depending on values that may or may not
     * already exist. Set a callback that updates the relate list position when the text changes
     * in the EditText field.
     */
    private fun fillViewHolder(viewHolder: HoleEntryViewHolder, position: Int) {
        viewHolder.holeNumber.text = (position + 1).toString()
        if (parValues[position] != null){
            viewHolder.parInput.setText(parValues[position].toString())
        }
        viewHolder.parInput.filters = arrayOf(HoleEntryFilter(3, 6))
        viewHolder.parInput.doAfterTextChanged {
            val text = viewHolder.parInput.text.toString()
            if (text == ""){
                parValues[position] = null
            } else if (parValues[position] != text){
                parValues[position] = text
            }
        }
        if (scoreValues[position] != null){
            viewHolder.scoreInput.setText(scoreValues[position].toString())
        }
        viewHolder.scoreInput.filters = arrayOf(HoleEntryFilter(1, 20))
        viewHolder.scoreInput.doAfterTextChanged {
            val text = viewHolder.scoreInput.text.toString()
            if (text == "") {
                scoreValues[position] = null
            } else if (scoreValues[position] != text) {
                scoreValues[position] = text
            }
        }
        if (puttValues[position] != null){
            viewHolder.puttsInput.setText(puttValues[position].toString())
        }
        viewHolder.puttsInput.filters = arrayOf(HoleEntryFilter(0, 19))
        viewHolder.puttsInput.doAfterTextChanged {
            val text = viewHolder.puttsInput.text.toString()
            if (text == ""){
                puttValues[position] = null
            } else if (puttValues[position] != text){
                puttValues[position] = text
            }
        }
    }
}

