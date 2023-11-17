package com.sparklead.anipedia.ui.favorite

import com.sparklead.anipedia.model.AnimeDb

sealed class FavoriteUiState {

    data object Loading : FavoriteUiState()

    data class Success(val list: List<AnimeDb>) : FavoriteUiState()

    data class Error(val message: String) : FavoriteUiState()

}