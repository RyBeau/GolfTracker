package com.rybeau.golfapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            this.getSupportActionBar()?.hide()
        } catch (error : NullPointerException) {}

        setContentView(R.layout.activity_main)
    }
}