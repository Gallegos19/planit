package com.example.planit.views.general_team.data.repository

import com.example.planit.core.network.RetrofitHelper
import retrofit2.Response

class DeleteGroupRepository {
    private val service = RetrofitHelper.getRetrofitGroup()

    suspend fun deleteGroup(id: Int): Result<String> {
        return try {

            val response = service.deleteGroup(id)

            if(response.isSuccessful) {
                Result.success(response.body()!!)
            }else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}