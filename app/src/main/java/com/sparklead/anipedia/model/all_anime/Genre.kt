package com.sparklead.anipedia.model.all_anime

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Genre(
    val mal_id: Int?,
    val name: String?,
    val type: String?,
    val url: String?
) :Parcelable