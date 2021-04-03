package com.rybeau.golfapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "round")
class Round (
    @ColumnInfo var date: String,
    @ColumnInfo var score: Int,
    @ColumnInfo var averagePutts: Double) {

    @PrimaryKey(autoGenerate = true) var id: Long = 0

    override fun toString(): String {
        return "Date: $date" +
                "Score: $score" +
                "Average Putts: ${String.format("%.1f", averagePutts)}"
    }
}