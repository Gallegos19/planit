package com.example.planit.core.data.local

import android.content.Context
import com.example.planit.core.data.local.personalActivity.repository.OfflinePersonalActivityInfoRepository


interface AppContainer {
    val personalActivityRepository: OfflinePersonalActivityInfoRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val personalActivityRepository: OfflinePersonalActivityInfoRepository = OfflinePersonalActivityInfoRepository(context)
}