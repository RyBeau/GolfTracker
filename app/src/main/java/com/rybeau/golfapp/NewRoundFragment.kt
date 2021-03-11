package com.rybeau.golfapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        createInput(view)
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