package com.example.planit.views.general_team.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateGroupDTO(
    val name: String,
    val description: String
)