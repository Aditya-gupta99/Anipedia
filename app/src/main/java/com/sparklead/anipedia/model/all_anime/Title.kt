package com.sparklead.anipedia.model.all_anime

import kotlinx.serialization.Serializable

@Serializable
data class Title(
    val title: String?,
    val type: String?
)