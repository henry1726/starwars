package com.example.pruebags.domain.repositories

import com.example.pruebags.data.service.GSApi
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val api : GSApi) {

    suspend fun getAllCharacters() = api.getAllCharacters()

}