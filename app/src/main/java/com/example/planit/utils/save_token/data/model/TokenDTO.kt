package com.example.planit.utils.save_token.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenDTO(
    private val id: Int,
    private val token: String
)
