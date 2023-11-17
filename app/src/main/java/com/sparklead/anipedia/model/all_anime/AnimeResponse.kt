package com.sparklead.anipedia.model.all_anime

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AnimeResponse(

    val background: String?,

    val episodes: Int?,

    val images: Images?,

    @PrimaryKey(autoGenerate = true)
    val mal_id: Int?,

    val rank: Int?,

    val score: Double?,

    val synopsis: String?,

    val title: String?,
) : Parcelable