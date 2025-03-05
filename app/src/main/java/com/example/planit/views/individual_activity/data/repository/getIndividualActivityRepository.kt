package com.example.planit.views.individual_activity.data.repository

import com.example.planit.components.left_bar.data.model.ActivityUserDTO
import com.example.planit.core.network.RetrofitHelper
import com.example.planit.views.individual_activity.data.model.IndividualActivityDTO

class getIndividualActivityRepository {
    private val service = RetrofitHelper.getRetrofitIndividualActivity()

    suspend fun getActivities(id: Int): Result<IndividualActivityDTO> {
        return try {
            val response = service.getIndividualActivity(id)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}