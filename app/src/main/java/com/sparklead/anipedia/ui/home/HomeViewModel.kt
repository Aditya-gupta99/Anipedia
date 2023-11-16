package com.sparklead.anipedia.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: AnimeListRepository) : ViewModel() {

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    fun getAllAnimeList() {
        _homeUiState.value = HomeUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllAnimeList()
                .catch {
                    _homeUiState.value = HomeUiState.Error(it.message.toString())
                }.collect {
                    _homeUiState.value = HomeUiState.AllAnimeListSuccess(it.data)
                }
        }
    }

    fun getTopAnimeList() {
        _homeUiState.value = HomeUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTopAnimeList()
                .catch {
                    _homeUiState.value = HomeUiState.Error(it.message.toString())
                }
                .collect {
                    _homeUiState.value = HomeUiState.TopAnimeSuccess(it.data)
                }
        }
    }
}