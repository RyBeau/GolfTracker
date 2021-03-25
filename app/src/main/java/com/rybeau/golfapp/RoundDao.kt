package com.rybeau.golfapp

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface RoundDao {

    @Insert
    suspend fun insert(round: Round): Long

    @Delete
    suspend fun delete(round:Round)

    @Query("SELECT * FROM round")
    fun getAll(): Flow<List<Round>>

    @Query("SELECT COUNT(*) FROM round")
    fun getTotalRounds(): Flow<Int>

    @Query("SELECT * FROM round ORDER BY id DESC LIMIT 10")
    fun getPrevious10(): Flow<List<Round>>
}