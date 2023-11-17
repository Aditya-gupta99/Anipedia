package com.sparklead.anipedia.ui.search

import com.sparklead.anipedia.model.all_anime.AnimeResponse

sealed class SearchUiState {

    data object Empty : SearchUiState()

    data class Success(val list: List<AnimeResponse>) : SearchUiState()

    data class Error(val message: String) : SearchUiState()
}