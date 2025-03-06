package com.example.planit.views.general_team.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GroupDTO (
    val id: Int,
    val user_id: Int,
    val name: String,
    val description: String,
    val token: String
)