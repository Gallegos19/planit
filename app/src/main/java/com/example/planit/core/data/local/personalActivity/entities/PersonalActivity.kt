package com.example.planit.core.data.local.personalActivity.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "personal_activity")
data class PersonalActivity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "activity_id")
    val activityId: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    val title: String
)
