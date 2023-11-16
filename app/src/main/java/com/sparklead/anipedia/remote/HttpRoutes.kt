package com.sparklead.anipedia.remote

object HttpRoutes {

    private const val BASE_URL = "https://api.jikan.moe/v4"

    const val ALL_ANIME_LIST = "$BASE_URL/anime"

    const val TOP_ANIME_LIST = "$BASE_URL/top/anime"
}