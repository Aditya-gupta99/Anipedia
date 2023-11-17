package com.sparklead.anipedia.model.all_anime

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Webp(
    val image_url: String?,
    val large_image_url: String?,
    val small_image_url: String?
) : Parcelable