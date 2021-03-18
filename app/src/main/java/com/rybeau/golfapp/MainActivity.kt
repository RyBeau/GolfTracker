package com.rybeau.golfapp


import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var location : Location

    enum class Location{
        HOME, NEW_ROUND, PREVIOUS_ROUNDS, OVERALL_STATS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        when(location){
            Location.NEW_ROUND -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(getString(R.string.cancel_confirmation))
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes) { _, _ ->
                            removeRecycleViewer(R.id.inputView)
                            super.onBackPressed()
                        }
                        .setNegativeButton(R.string.no){ dialog, _ ->
                            dialog.dismiss()
                        }
                val alert = builder.create()
                alert.show()
            }
            Location.PREVIOUS_ROUNDS -> {
                removeRecycleViewer(R.id.roundsView)
                super.onBackPressed()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    fun setLocation(currentLocation: Location) {
        location = currentLocation
    }

    fun getLocation(): Location {
        return location
    }

    private fun removeRecycleViewer(id : Int) {
        val recyclerView = findViewById<RecyclerView>(id)
        recyclerView?.visibility = View.GONE
    }
}