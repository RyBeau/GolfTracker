package com.rybeau.golfapp

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class HomeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allowReturnTransitionOverlap = false
        allowEnterTransitionOverlap = false

        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_out)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.newRound)?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_newRoundFragment, null)
        )
        view.findViewById<Button>(R.id.previousRounds)?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_previousRoundsFragment, null)
        )

        view.findViewById<Button>(R.id.overallStats)?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_overallStatsFragment, null)
        )
    }
}