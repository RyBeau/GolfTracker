package com.rybeau.golfapp

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RoundViewModel(private val roundRepository: RoundRepository): ViewModel() {
    val allRounds: LiveData<List<Round>> = roundRepository.allRounds.asLiveData()
    val totalRounds: LiveData<Int> = roundRepository.totalRounds.asLiveData()
    val previous10Rounds: LiveData<List<Round>> = roundRepository.previous10Rounds.asLiveData()

    fun addRound(round: Round) = viewModelScope.launch {
        roundRepository.insert(round)
    }

    fun deleteRound(round: Round) = viewModelScope.launch {
        roundRepository.delete(round)
    }
}

class RoundViewModelFactory(private val repository: RoundRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoundViewModel::class.java)){
            @Suppress( "UNCHECKED_CAST")
            return RoundViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}