package com.sparklead.anipedia.serviceImp

import com.sparklead.anipedia.model.all_anime.AnimeModel
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.remote.HttpRoutes
import com.sparklead.anipedia.service.AnimeService
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AnimeServiceImp(private val client: HttpClient) : AnimeService {

    override suspend fun getAllAnimeList(): AnimeModel {
        return try {
            client.get {
                url(HttpRoutes.ALL_ANIME_LIST)
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            throw e
        }
    }
}