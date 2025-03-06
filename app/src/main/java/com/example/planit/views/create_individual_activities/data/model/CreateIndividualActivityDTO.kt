package com.example.planit.views.create_individual_activities.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateIndividualActivityDTO(
    private val user_id: Int,
    private val title: String,
    private val category_id: Int,
    private val status: String,
    private val description: String,
    private val date: String
)
