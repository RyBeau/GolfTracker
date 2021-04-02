package com.rybeau.golfapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PreviousRoundsFragment : TransitionFragment(), RoundAdapter.OnRoundListener {

    private val viewModel: RoundViewModel by activityViewModels() {
        RoundViewModelFactory((requireActivity().application as GolfTrackerRoomApplication).repository)
    }

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

        val view = inflater.inflate(R.layout.fragment_previous_rounds, container, false)

        val roundAdapter = RoundAdapter(listOf(), this)
        viewModel.allRounds.observe(viewLifecycleOwner, { newRounds ->
            roundAdapter.setData(newRounds)
        })

        val recyclerView: RecyclerView = view.findViewById(R.id.roundsView)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = roundAdapter
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    override fun onRoundClick(position: Int) {
        val options = arrayOf(getString(R.string.share), getString(R.string.delete))
        val builder = AlertDialog.Builder(activity)
        builder.setItems(options) { _, optionId ->
            dispatchActon(optionId, viewModel.allRounds.value!![position])
        }
        builder.show()
    }

    private fun dispatchActon(optionId: Int, round: Round) {
        when (optionId) {
            0 -> textAction(round)
            1 -> confirmDelete(round)
        }
    }

    private fun textAction(round : Round){
        val intent = Intent(Intent.ACTION_SEND)
        val textContent = "I scored ${if(round.score > 0) "+" else ""} ${round.score} in golf on ${round.date} with an average of ${String.format("%.1f", round.averagePutts)} putts per hole"
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_using))
        intent.putExtra(Intent.EXTRA_TEXT, textContent)

        startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
    }

    private fun confirmDelete(round: Round){
        val builder = AlertDialog.Builder(context)
        builder.setMessage(getString(R.string.delete_confirmation))
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteRound(round)
            }
            .setNegativeButton(R.string.no){ dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}