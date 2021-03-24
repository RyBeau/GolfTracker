package com.rybeau.golfapp

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.navigation.fragment.findNavController
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
        val listView = view.findViewById<ListView>(R.id.inputView)
        val parValues = (listView.adapter as HoleEntryAdapter).getParValues()
        val scoreValues = (listView.adapter as HoleEntryAdapter).getScoreValues()
        val puttsValues = (listView.adapter as HoleEntryAdapter).getPuttValues()
        if(null in parValues || null in scoreValues || null in puttsValues){
            valid = false
        }
        Log.d("Testing", "Par Values: $parValues\n Score Values: $scoreValues\n Putts Values: $puttsValues")
        return valid
    }

    private fun updateHoles(holes: Int) {
        if (holes != numHoles){
            numHoles = holes
            val listViewAdapter = view?.findViewById<ListView>(R.id.inputView)?.adapter as HoleEntryAdapter
            val parValues: MutableList<String?>
            val scoreValues: MutableList<String?>
            val puttValues: MutableList<String?>

            if (holes == 9) {
                parValues = listViewAdapter.getParValues().subList(0, numHoles) as MutableList
                scoreValues = listViewAdapter.getScoreValues().subList(0, numHoles) as MutableList
                puttValues = listViewAdapter.getPuttValues().subList(0, numHoles) as MutableList
            } else {
                parValues = (listViewAdapter.getParValues() + MutableList<String?>(holes/2){null}) as MutableList<String?>
                scoreValues = (listViewAdapter.getScoreValues() + MutableList<String?>(holes/2){null}) as MutableList<String?>
                puttValues = (listViewAdapter.getPuttValues() + MutableList<String?>(holes/2){null}) as MutableList<String?>
            }
            this.view?.let { createInput(it, parValues, scoreValues, puttValues) }
        }
    }

    private fun createInput(view: View, existingParValues: MutableList<String?>, existingScoreValues: MutableList<String?>, existingPuttValues: MutableList<String?>) {
        val listView = view.findViewById<ListView>(R.id.inputView)
        listView.adapter = HoleEntryAdapter(requireContext(), numHoles, existingParValues, existingScoreValues, existingPuttValues)
    }
    private fun createInput(view: View){
        val listView = view.findViewById<ListView>(R.id.inputView)
        listView.adapter = HoleEntryAdapter(requireContext(), numHoles)
    }
}