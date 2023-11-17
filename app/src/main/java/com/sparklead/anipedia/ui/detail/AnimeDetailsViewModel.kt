package com.sparklead.anipedia.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparklead.anipedia.model.AnimeDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(private val repository: AnimeDbRepository) :
    ViewModel() {

    private val _animeDetailUiState = MutableStateFlow<AnimeDetailUiState>(AnimeDetailUiState.Empty)
    val animeDetailUiState get() = _animeDetailUiState

    fun saveAnimeInDB(animeDb: AnimeDb) = viewModelScope.launch {
        repository.saveAnimeDb(animeDb)
    }

    fun unSaveAnimeInDB(animeDb: AnimeDb) = viewModelScope.launch {
        repository.unSaveAnimeDb(animeDb)
    }

    fun getAnimeCount(title: String) = viewModelScope.launch {
        repository.getAnimeCount(title)
            .catch {
                _animeDetailUiState.value = AnimeDetailUiState.Error(it.message.toString())
            }.collect {
                _animeDetailUiState.value = AnimeDetailUiState.SuccessCount(it)
            }
    }
}