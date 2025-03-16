package com.example.planit.views.create_individual_activities.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateIndividualActivityDTO(
    val user_id: Int,
    val title: String,
    val category_id: Int,
    val status: String,
    val description: String,
    val date: String
)
