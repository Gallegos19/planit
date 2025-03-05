package com.example.planit.components.left_bar.data.repository

import com.example.planit.components.left_bar.data.model.ActivityUserDTO
import com.example.planit.core.network.RetrofitHelper

class GetActivityUserRepository {
    private val service = RetrofitHelper.getRetrofitLeftBar()

    suspend fun getActivities(id: Int): Result<List<ActivityUserDTO>> {
        return try {
            val response = service.getActivitiesUser(id)
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