package com.example.planit.components.left_bar.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ActivityUserDTO(
    val activity_id: Int,
    val title: String
)
