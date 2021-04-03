package com.rybeau.golfapp

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment for New Round Entry Page
 */
class NewRoundFragment : TransitionFragment() {

        /**
         * ViewModel for the Room database
         * */
        private val viewModel: RoundViewModel by activityViewModels() {
            RoundViewModelFactory((requireActivity().application as GolfTrackerRoomApplication).repository)
        }

        /**
         * Current number of holes to display hole entry fields for
         * */
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

        /**
         * Saves the state of the dynamic EditText widgets to the bundle
         * for orientation changes.
         */
        override fun onSaveInstanceState(outState: Bundle) {
                super.onSaveInstanceState(outState)

                outState.putInt("Holes", numHoles)
                val listViewAdapter = view?.findViewById<ListView>(R.id.inputView)?.adapter as HoleEntryAdapter
                outState.putStringArrayList("Pars", listViewAdapter.getParValues() as ArrayList<String?>)
                outState.putStringArrayList("Scores", listViewAdapter.getScoreValues() as ArrayList<String?>)
                outState.putStringArrayList("Putts", listViewAdapter.getPuttValues() as ArrayList<String?>)
        }

        /**
         * Restore dynamic EditText values if the keys exist in bundle.
         */
        override fun onViewStateRestored(savedInstanceState: Bundle?) {
            super.onViewStateRestored(savedInstanceState)
            val parValues: MutableList<String?>
            val scoreValues: MutableList<String?>
            val puttValues: MutableList<String?>

            if (savedInstanceState?.containsKey("Holes") == true){
                numHoles = savedInstanceState.getInt("Holes")
            }

            if (savedInstanceState?.containsKey("Pars") == true && savedInstanceState.containsKey("Scores") && savedInstanceState.containsKey("Putts")){
                parValues = savedInstanceState.getStringArrayList("Pars") as MutableList<String?>
                scoreValues = savedInstanceState.getStringArrayList("Scores") as MutableList<String?>
                puttValues = savedInstanceState.getStringArrayList("Putts") as MutableList<String?>

                createInput(requireView(), parValues, scoreValues, puttValues)
            }
        }

        /**
         * Converts the values entered in the dynamic EditText widgets to a Round object, saves
         * object to the Room Database.
         */
        private fun enterNewRound(view: View){
            val listViewAdapter = view.findViewById<ListView>(R.id.inputView).adapter as HoleEntryAdapter
            val parValues = listViewAdapter.getParValues()
            val scoreValues = listViewAdapter.getScoreValues()
            val puttsValues = listViewAdapter.getPuttValues()
            if(validateEntries(parValues, scoreValues, puttsValues)){
                @Suppress("UNCHECKED_CAST")
                val round: Round = convertToRound(parValues as List<String>, scoreValues as List<String>, puttsValues as List<String>)
                viewModel.addRound(round)
                (activity as MainActivity).removeListView(R.id.inputView)
                returnTransition = inflater.inflateTransition(R.transition.slide_out)
                findNavController().navigate(R.id.action_newRoundFragment_to_previousRoundsFragment)
            } else {
                Toast.makeText(activity, getString(R.string.invalid_entries), Toast.LENGTH_LONG).show()
            }
        }

        /**
         * Checks that no score values in the lists are null and scores are possible
         * */
        private fun validateEntries(parValues: List<String?>,scoreValues: List<String?>, puttsValues: List<String?>): Boolean{
            var valid = true
            @Suppress("UNCHECKED_CAST")
            if(null in parValues || null in scoreValues || null in puttsValues){
                valid = false
            } else if (!scoresPossible(scoreValues as List<String>, puttsValues as List<String>)){
                valid = false
            }
            return valid
        }

        /**
         * Checks that the putts do not exceed 1 less than the entered score.
         * */
        private fun scoresPossible(scores: List<String>, putts: List<String>): Boolean{
            for (i in scores.indices){
                if((scores[i].toInt() - putts[i].toInt()) < 1){
                    return false
                }
            }
            return true
        }

        /**
         * Converts the given par, score and putts list into a Round Object
         * */
        private fun convertToRound(parValues: List<String>, scoreValues: List<String>, puttsValues: List<String>): Round{
            var totalPar = 0
            var totalScore = 0
            var totalPutts = 0
            val totalHoles = parValues.size
            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val currentDateTime: String = simpleDateFormat.format(Date())
            for (i in 0 until totalHoles){
                totalPar += parValues[i].toInt()
                totalScore += scoreValues[i].toInt()
                totalPutts += puttsValues[i].toInt()
            }
            return Round(currentDateTime, totalScore - totalPar, totalPutts.toDouble() / totalHoles.toDouble())
        }

        /**
         * Updates the number of displayed entry fields in the ListView. Saves existing entered values
         * and repopulates the new fields with the existing values.
         * */
        private fun updateHoles(holes: Int) {
            if (holes != numHoles){
                numHoles = holes
                val listViewAdapter = view?.findViewById<ListView>(R.id.inputView)?.adapter as HoleEntryAdapter
                val parValues: MutableList<String?>
                val scoreValues: MutableList<String?>
                val puttValues: MutableList<String?>

                if (holes == 9) {
                    parValues = listViewAdapter.getParValues().slice(0 until numHoles) as MutableList<String?>
                    scoreValues = listViewAdapter.getScoreValues().slice(0 until numHoles) as MutableList<String?>
                    puttValues = listViewAdapter.getPuttValues().slice(0 until numHoles) as MutableList<String?>
                } else {
                    parValues = (listViewAdapter.getParValues() + MutableList<String?>(holes/2){null}) as MutableList<String?>
                    scoreValues = (listViewAdapter.getScoreValues() + MutableList<String?>(holes/2){null}) as MutableList<String?>
                    puttValues = (listViewAdapter.getPuttValues() + MutableList<String?>(holes/2){null}) as MutableList<String?>
                }
                this.view?.let { createInput(it, parValues, scoreValues, puttValues) }
            }
        }

        /**
         * Creates the ListView.adapter using existing values for par, score and putts.
         * */
        private fun createInput(view: View, existingParValues: MutableList<String?>, existingScoreValues: MutableList<String?>, existingPuttValues: MutableList<String?>) {
            val listView = view.findViewById<ListView>(R.id.inputView)
            listView.adapter = HoleEntryAdapter(requireContext(), numHoles, existingParValues, existingScoreValues, existingPuttValues)
        }

        /**
         * Creates the ListView.adapter with no existing values.
         * */
        private fun createInput(view: View){
            val listView = view.findViewById<ListView>(R.id.inputView)
            listView.adapter = HoleEntryAdapter(requireContext(), numHoles)
        }
}
