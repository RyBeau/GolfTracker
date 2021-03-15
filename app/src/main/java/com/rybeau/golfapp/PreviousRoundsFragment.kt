package com.rybeau.golfapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate

class PreviousRoundsFragment : Fragment() {

    private val rounds = arrayOf(
        Round("9/1/2020", "-3", 2),
        Round("8/1/2020", "0", 2),
        Round("7/1/2020", "+3", 3),
        Round("6/1/2020", "+1", 2),
        Round("5/1/2020", "-1", 2),
        Round("4/1/2020", "+15", 5),
        Round("3/1/2020", "+12", 4),
        Round("2/1/2020", "-5", 1),
        Round("1/1/2020", "+1", 3),
        Round("9/1/2020", "-3", 2),
        Round("8/1/2020", "0", 2),
        Round("7/1/2020", "+3", 3),
        Round("6/1/2020", "+1", 2),
        Round("5/1/2020", "-1", 2),
        Round("4/1/2020", "+15", 5),
        Round("3/1/2020", "+12", 4),
        Round("2/1/2020", "-5", 1),
        Round("1/1/2020", "+1", 3),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_previous_rounds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener{
            requireActivity().onBackPressed()
        }

        val roundAdapter = RoundAdapter(rounds)
        val recyclerView: RecyclerView = view.findViewById(R.id.roundsView)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = roundAdapter
        }
    }
}