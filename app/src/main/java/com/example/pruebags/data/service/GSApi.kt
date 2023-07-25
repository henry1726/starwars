package com.example.pruebags.data.service

import com.example.pruebags.data.models.CharacterResponse
import com.example.pruebags.data.models.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET


interface GSApi {

    @GET("/api/character/1,2,3,4,5")
    suspend fun getAllCharacters()
    : Response<CharacterResponse>

}