package com.example.planit.core.data.local.personalActivity.repository

import com.example.planit.core.data.local.personalActivity.entities.PersonalActivity
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivityInfo
import com.example.planit.core.data.local.personalActivity.relations.PersonalActivityWithInfo
import kotlinx.coroutines.flow.Flow

interface PersonalActivityInfoRepository {

    suspend fun insertPersonalActivityWithInfo(personalActivityInfo: PersonalActivityInfo, personalActivity: PersonalActivity)

    fun findPersonalActivityInfo(id: Int): PersonalActivityWithInfo

    fun findPersonalActivities(): List<PersonalActivity>
}