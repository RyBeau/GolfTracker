package com.rybeau.golfapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class OverallStatsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overall_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateStats(view)
    }

    private fun populateStats(view: View) {
        val averageScore = view.findViewById<TextView>(R.id.averageScore)
        val handicap = view.findViewById<TextView>(R.id.handicap)
        val puttsPerHole = view.findViewById<TextView>(R.id.puttsPerHole)
        val totalRounds = view.findViewById<TextView>(R.id.totalRounds)

        averageScore.text = "+13"
        handicap.text = "13"
        puttsPerHole.text = "3"
        totalRounds.text = "115"
    }

}