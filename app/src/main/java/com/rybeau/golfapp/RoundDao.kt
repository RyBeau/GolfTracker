package com.rybeau.golfapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoundDao {

    @Insert
    suspend fun insert(round: Round): Long

    @Delete
    suspend fun delete(round:Round)

    /**
     * Gets all rounds
     */
    @Query("SELECT * FROM round ORDER BY id DESC")
    fun getAll(): Flow<List<Round>>

    /**
     * Gets the total number of rounds
     */
    @Query("SELECT COUNT(*) FROM round")
    fun getTotalRounds(): Flow<Int>

    /**
     * Select the 10 most recent rounds
     */
    @Query("SELECT score FROM (SELECT score, id FROM round ORDER BY id DESC LIMIT 10)")
    fun getPrevious10Score(): Flow<List<Int>>

    /**
     * Gets the average score
     */
    @Query("SELECT AVG(score) FROM round")
    fun getAverageScore(): Flow<Double>

    /**
     * Gets the average putts
     */
    @Query("SELECT AVG(averagePutts) FROM round")
    fun getAveragePutts(): Flow<Double>
}