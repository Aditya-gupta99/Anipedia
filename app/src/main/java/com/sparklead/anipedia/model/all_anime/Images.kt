package com.sparklead.anipedia.model.all_anime

import kotlinx.serialization.Serializable

@Serializable
data class Images(
    val jpg: Jpg?,
    val webp: Webp?
)