package com.example.planit.core.data.local.personalActivity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivity
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivityInfo

data class PersonalActivityWithInfo(
    @Embedded val personalActivityInfo: PersonalActivityInfo,
    @Relation(
        parentColumn = "id",
        entityColumn = "activity_id"
    )
    val personalActivity: PersonalActivity
)
