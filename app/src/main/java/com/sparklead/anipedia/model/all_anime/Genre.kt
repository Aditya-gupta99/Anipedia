package com.sparklead.anipedia.model.all_anime

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val mal_id: Int?,
    val name: String?,
    val type: String?,
    val url: String?
)