package com.rybeau.golfapp

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class RoundRepository(private val roundDao: RoundDao) {
    val allRounds: Flow<List<Round>> = roundDao.getAll()
    val totalRounds: Flow<Int> = roundDao.getTotalRounds()
    val previous10RoundsScore: Flow<List<Int>> = roundDao.getPrevious10Score()
    val averageScore: Flow<Double> = roundDao.getAverageScore()
    val averagePutts: Flow<Double> = roundDao.getAveragePutts()

    @WorkerThread
    suspend fun insert(round: Round){
        roundDao.insert(round)
    }

    @WorkerThread
    suspend fun delete(round: Round){
        roundDao.delete(round)
    }
}