package com.sparklead.anipedia.ui.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparklead.anipedia.ui.detail.AnimeDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: AnimeDbRepository) :
    ViewModel() {

    private val _favoriteUiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Loading)
    val favoriteUiState: StateFlow<FavoriteUiState> get() = _favoriteUiState

    fun getAllAnimeList() = viewModelScope.launch {
        repository.getAllAnime()
            .catch {
                _favoriteUiState.value = FavoriteUiState.Error(it.message.toString())
                Log.e("@@@",it.toString())
            }
            .collect {
                _favoriteUiState.value = FavoriteUiState.Success(it)
                Log.e("@@@",it.toString())
            }
    }

}