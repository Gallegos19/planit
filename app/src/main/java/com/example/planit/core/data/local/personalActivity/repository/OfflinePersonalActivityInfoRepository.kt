package com.example.planit.core.data.local.personalActivity.repository

import android.content.Context
import com.example.planit.core.data.local.PlanItDatabase
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivity
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivityInfo
import com.example.planit.core.data.local.personalActivity.relations.PersonalActivityWithInfo
import com.google.common.primitives.UnsignedBytes.toInt


class OfflinePersonalActivityInfoRepository(context: Context): PersonalActivityInfoRepository {

    private val personalActivityInfoDao = PlanItDatabase.getDatabase(context).personalActivityInfoDao()
    private val personalActivityDao = PlanItDatabase.getDatabase(context).personalActivityDao()


    override suspend fun insertPersonalActivityWithInfo(
        personalActivityInfo: PersonalActivityInfo,
        personalActivity: PersonalActivity,
    ) {
        val infoId = personalActivityInfoDao.insert(personalActivityInfo)

        if (infoId == -1L) {
            throw Exception("Error al insertar la información de la actividad")
        }

        val personalActivityWithInfo = personalActivity.copy(activityId = infoId.toInt())
        personalActivityDao.insert(personalActivityWithInfo)
    }

    override fun findPersonalActivityInfo(id: Int): PersonalActivityWithInfo = personalActivityInfoDao.findOne(id)

    override fun findPersonalActivities(): List<PersonalActivity> = personalActivityDao.findAll()

    override suspend fun findMaxId(): Long = personalActivityInfoDao.findMaxId()

}