package com.rybeau.golfapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Round::class], version = 1)
abstract class RoundDatabase: RoomDatabase() {
    abstract fun roundDao(): RoundDao

    companion object{

        @Volatile
        private var INSTANCE: RoundDatabase? = null

        fun getDatabase(context: Context): RoundDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoundDatabase::class.java,
                    "round_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}