package com.sparklead.anipedia.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparklead.anipedia.ui.home.AnimeListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: AnimeListRepository) :
    ViewModel() {

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Empty)
    val searchUiState get() = _searchUiState

    fun getSearchAnimeList(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSearch(text)
                .catch {
                    _searchUiState.value = SearchUiState.Error(it.message.toString())
                }
                .collect {
                    _searchUiState.value = SearchUiState.Success(it.data)
                }
        }
    }

}