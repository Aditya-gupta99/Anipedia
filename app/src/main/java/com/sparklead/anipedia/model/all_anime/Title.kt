package com.sparklead.anipedia.model.all_anime

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Title(
    val title: String?,
    val type: String?
) : Parcelable