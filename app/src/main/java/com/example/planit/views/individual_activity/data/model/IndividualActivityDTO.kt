package com.example.planit.views.individual_activity.data.model

import DateSerializer
import kotlinx.serialization.Serializable
import java.sql.Date

@Serializable
data class IndividualActivityDTO(
    val title: String,
    val category: String,
    val status: String,
    val description: String,
    @Serializable(with = DateSerializer::class)
    val date: Date
)
