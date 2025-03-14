package com.example.planit.core.data.local.personalActivity.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personal_activity_info")
data class PersonalActivityInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    val status: String,
    val description: String,
    @ColumnInfo(name = "date_to")
    val dateTo: String
)
