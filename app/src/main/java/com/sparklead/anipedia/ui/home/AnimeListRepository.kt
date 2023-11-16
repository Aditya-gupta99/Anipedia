package com.sparklead.anipedia.ui.home

import com.sparklead.anipedia.model.all_anime.AnimeModel
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import kotlinx.coroutines.flow.Flow

interface AnimeListRepository {

    fun getAllAnimeList() : Flow<AnimeModel>

}