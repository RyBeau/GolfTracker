package com.rybeau.golfapp

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment

open class TransitionFragment: Fragment() {

    lateinit var inflater : TransitionInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflater = TransitionInflater.from(requireContext())
        allowReturnTransitionOverlap = false
        allowEnterTransitionOverlap = false
    }
}