package com.sparklead.anipedia.model.all_anime

import kotlinx.serialization.Serializable

@Serializable
data class Webp(
    val image_url: String?,
    val large_image_url: String?,
    val small_image_url: String?
)