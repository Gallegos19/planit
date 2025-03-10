package com.example.planit.views.individual_activity.data.model

import kotlinx.serialization.Serializable

@Serializable
data class IndividualActivityDTO(
    val title: String,
    val category: String,
    val status: String,
    val description: String,
    val date: String
)
