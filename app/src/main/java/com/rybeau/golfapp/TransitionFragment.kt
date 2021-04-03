package com.rybeau.golfapp

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment

/**
 * Super class from all fragments. It contains the constant code required for configuring
 * the transition animations.
 */
open class TransitionFragment: Fragment() {

    lateinit var inflater : TransitionInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflater = TransitionInflater.from(context)
        allowReturnTransitionOverlap = false
        allowEnterTransitionOverlap = false
    }
}