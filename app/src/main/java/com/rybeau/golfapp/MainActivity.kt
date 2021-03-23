package com.rybeau.golfapp


import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
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
                onBackNewRound()
            }
            Location.PREVIOUS_ROUNDS -> {
                removeListView(R.id.roundsView)
                super.onBackPressed()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    fun removeListView(id : Int) {
        val view = findViewById<View>(id)
        view?.visibility = View.GONE
    }

    fun setLocation(currentLocation: Location) {
        location = currentLocation
    }

    fun getLocation(): Location {
        return location
    }

    private fun onBackNewRound(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.cancel_confirmation))
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { _, _ ->
                    removeListView(R.id.inputView)
                    super.onBackPressed()
                }
                .setNegativeButton(R.string.no){ dialog, _ ->
                    dialog.dismiss()
                }
        val alert = builder.create()
        alert.show()
    }
}