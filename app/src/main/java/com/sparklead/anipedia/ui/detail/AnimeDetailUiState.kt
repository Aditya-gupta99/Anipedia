package com.sparklead.anipedia.ui.detail

sealed class AnimeDetailUiState {

    data object Empty : AnimeDetailUiState()

    data class SuccessCount(val count : Int) : AnimeDetailUiState()

    data class Error(val message: String) : AnimeDetailUiState()
}
