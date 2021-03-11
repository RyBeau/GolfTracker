package com.rybeau.golfapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
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

        nineButton.setOnClickListener{
            updateHoles(9)
        }

        eighteenButton.setOnClickListener{
            updateHoles(18)
        }

        createInput(view)
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