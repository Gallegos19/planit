package com.example.planit.utils.save_token.domain

import com.example.planit.utils.save_token.data.model.TokenDTO
import com.example.planit.utils.save_token.data.repository.SaveTokenRepository


class SaveTokenUseCase {

    private val repository = SaveTokenRepository()

    suspend fun saveToken(bodyToken: TokenDTO) : Result<String>{
        val result = repository.saveToken(bodyToken)
        return result
    }
}