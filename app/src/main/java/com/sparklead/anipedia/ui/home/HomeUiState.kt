package com.sparklead.anipedia.ui.home

import com.sparklead.anipedia.model.all_anime.AnimeResponse

sealed class HomeUiState {

    data object Loading : HomeUiState()

    data class Success(val animeList: List<AnimeResponse>) : HomeUiState()

    data class Error(val message: String) : HomeUiState()

}
