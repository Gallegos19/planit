package com.example.planit.views.individual_activity.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateIndividualActivityDTO(
    val user_id : Int,
    val title: String,
    val category_id: Int,
    val status: String,
    val description: String,
    val date: String
)
