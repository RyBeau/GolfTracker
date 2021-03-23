package com.rybeau.golfapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip

class NewRoundFragment : TransitionFragment() {

    private var numHoles: Int = 9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = inflater.inflateTransition(R.transition.slide_in)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mainActivity = activity as MainActivity
        mainActivity.setLocation(MainActivity.Location.NEW_ROUND)
        return inflater.inflate(R.layout.fragment_new_round, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nineButton = view.findViewById<Chip>(R.id.nineHoles)
        val eighteenButton = view.findViewById<Chip>(R.id.eighteenHoles)
        val addRoundButton = view.findViewById<Button>(R.id.addRoundButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)

        addRoundButton.setOnClickListener{
            enterNewRound(view)
        }
        cancelButton.setOnClickListener{
            requireActivity().onBackPressed()
        }
        nineButton.setOnClickListener{
            updateHoles(9)
        }
        eighteenButton.setOnClickListener{
            updateHoles(18)
        }

        createInput(view)
    }

    private fun enterNewRound(view: View){
        if(validateEntries(view)){
            (activity as MainActivity).removeListView(R.id.inputView)
            returnTransition = inflater.inflateTransition(R.transition.slide_out)
            findNavController().navigate(R.id.action_newRoundFragment_to_previousRoundsFragment)
        } else {
            Toast.makeText(activity, getString(R.string.invalid_entries), Toast.LENGTH_LONG).show()
        }
    }

    private fun validateEntries(view: View): Boolean{
        var valid = true
        for (i in 1..numHoles){
            val par = view.findViewWithTag<EditText>("par$i")
            val score = view.findViewWithTag<EditText>("score$i")
            val putts = view.findViewWithTag<EditText>("putts$i")

            if (par.text.toString().isEmpty() || score.text.toString().isEmpty() || putts.text.toString().isEmpty()){
                valid = false
                break
            }
        }
        return valid
    }

    private fun updateHoles(holes: Int) {
        if (holes != numHoles){
            numHoles = holes
            this.view?.let { createInput(it) }
        }
    }

    private fun createInput(view: View){
        val listView = view.findViewById<ListView>(R.id.inputView)
        listView.adapter = HoleEntryAdapter(requireContext(), IntArray(numHoles){it + 1})
    }
}