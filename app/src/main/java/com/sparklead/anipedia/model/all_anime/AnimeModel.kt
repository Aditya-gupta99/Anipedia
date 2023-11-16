package com.sparklead.anipedia.model.all_anime

import kotlinx.serialization.Serializable

@Serializable
data class AnimeModel(
    val data : List<AnimeResponse>
)
