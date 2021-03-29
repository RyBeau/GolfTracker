package com.rybeau.golfapp

import android.app.Application

class GolfTrackerRoomApplication: Application() {
    val database by lazy { RoundDatabase.getDatabase(this) }
    val repository by lazy { RoundRepository(database.roundDao())}
}