package com.rybeau.golfapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip

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

        val nineButton = view.findViewById<Chip>(R.id.nineHoles)
        val eighteenButton = view.findViewById<Chip>(R.id.eighteenHoles)

        val addRoundButton = view.findViewById<Button>(R.id.addRoundButton)
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)

        addRoundButton.setOnClickListener{
            enterNewRound(view)
        }

        cancelButton.setOnClickListener{
            cancelConfirmation()
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
            Navigation.createNavigateOnClickListener(R.id.action_newRoundFragment_to_previousRoundsFragment, null )
        } else {
            Toast.makeText(activity, getString(R.string.invalid_entries), Toast.LENGTH_LONG).show()
        }
    }

    private fun validateEntries(view: View): Boolean{
        return false
    }

    private fun cancelConfirmation(){
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(getString(R.string.cancel_confirmation))
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { _, _ ->
                    requireActivity().onBackPressed()
                }
                .setNegativeButton(R.string.no){ dialog, _ ->
                    dialog.dismiss()
                }
        val alert = builder.create()
        alert.show()
    }

    private fun updateHoles(holes: Int) {
        if (holes != numHoles){
            numHoles = holes
            this.view?.let { createInput(it) }
        }
    }

    private fun createInput(view: View){
        val holeEntryAdapter = HoleEntryAdapter(IntArray(numHoles){it + 1})
        val recyclerView: RecyclerView = view.findViewById(R.id.inputView)
        recyclerView.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = holeEntryAdapter
        }
    }
}