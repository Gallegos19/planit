package com.example.planit.views.add_team.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TeamDTO(
    private val user_id: Int,
    private val token:String
)
