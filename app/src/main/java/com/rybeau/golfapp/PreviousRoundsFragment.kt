package com.rybeau.golfapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate

class PreviousRoundsFragment : TransitionFragment(), RoundAdapter.OnRoundListener {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = inflater.inflateTransition(R.transition.slide_in)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        mainActivity.setLocation(MainActivity.Location.PREVIOUS_ROUNDS)
        return inflater.inflate(R.layout.fragment_previous_rounds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<Button>(R.id.backButton)
        val recyclerView: RecyclerView = view.findViewById(R.id.roundsView)

        backButton.setOnClickListener{
            requireActivity().onBackPressed()
        }

        val roundAdapter = RoundAdapter(rounds, this)

        recyclerView.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = roundAdapter
        }
    }

    override fun onRoundClick(position: Int) {
        val options = arrayOf(getString(R.string.share))
        val builder = AlertDialog.Builder(activity)
        builder.setItems(options) { _, optionId ->
            dispatchActon(optionId, rounds[position])
        }
        builder.show()
    }

    private fun dispatchActon(optionId: Int, round: Round) {
        when (optionId) {
            0 -> textAction(round)
        }
    }

    private fun textAction(round : Round){
        val intent = Intent(Intent.ACTION_SEND)
        val textContent = "I scored ${round.score} in golf on ${round.date} with an average of ${round.averagePutts} putts per hole"
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_using))
        intent.putExtra(Intent.EXTRA_TEXT, textContent)

        startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
    }
}