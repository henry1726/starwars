package com.example.pruebags.data.service

import com.example.pruebags.data.models.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET


interface GSApi {

    @GET("api/all.json")
    suspend fun getAllCharacters()
    : Response<CharactersResponse>

}