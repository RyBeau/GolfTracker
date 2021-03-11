package com.rybeau.golfapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class NewRoundFragment : Fragment() {

    private var numHoles: Int = 9

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_round, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createInput(view)
    }

    private fun createInput(view: View){
        val inputLayout: LinearLayout = view.findViewById(R.id.inputLayout)

        for (i in 1..numHoles){
            val holeLayout = LinearLayout(activity)
            val holeText = TextView(activity)
            holeText.text = i.toString()
            val parEntry = EditText(activity)
            val scoreEntry = EditText(activity)
            val puttsEntry = EditText(activity)
            holeLayout.addView(holeText)
            holeLayout.addView(parEntry)
            holeLayout.addView(scoreEntry)
            holeLayout.addView(puttsEntry)
            inputLayout.addView(holeLayout)
        }
    }
}