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

/**
 * Fragment for Previous Rounds Page
 */
class PreviousRoundsFragment : TransitionFragment(), RoundAdapter.OnRoundListener {

    /**
     * ViewModel for the Room database
     * */
    private val viewModel: RoundViewModel by activityViewModels() {
        RoundViewModelFactory((requireActivity().application as GolfTrackerRoomApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = inflater.inflateTransition(R.transition.slide_in)
    }

    /**
     * Inflates the RecyclerView with previous rounds from the Room Database.
     */
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

    /**
     * Callback method for when a round is clicked in the RecyclerView.
     * Create dialog with possible actions.
     */
    override fun onRoundClick(position: Int) {
        val options = arrayOf(getString(R.string.share), getString(R.string.delete))
        val builder = AlertDialog.Builder(activity)
        builder.setItems(options) { _, optionId ->
            dispatchActon(optionId, viewModel.allRounds.value!![position])
        }
        builder.show()
    }

    /**
     * Dispatches the appropriate action given which dialog option was selected.
     * */
    private fun dispatchActon(optionId: Int, round: Round) {
        when (optionId) {
            0 -> textAction(round)
            1 -> confirmDelete(round)
        }
    }

    /**
     * Dispatches Implicit Intent for sending a message about a given round.
     * Works with Email, Text, Twitter etc.
     */
    private fun textAction(round : Round){
        val intent = Intent(Intent.ACTION_SEND)
        val textContent = "I scored ${if(round.score > 0) "+" else ""} ${round.score} in golf on ${round.date} with an average of ${String.format("%.1f", round.averagePutts)} putts per hole"
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
        intent.putExtra(Intent.EXTRA_TEXT, textContent)

        startActivity(Intent.createChooser(intent, getString(R.string.share_using)))
    }

    /**
     * Creates confirmation dialog to confirm deletion.
     */
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