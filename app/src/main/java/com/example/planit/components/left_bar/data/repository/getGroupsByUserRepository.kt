package com.example.planit.components.left_bar.data.repository

import com.example.planit.components.left_bar.data.model.GroupDTO
import com.example.planit.core.network.RetrofitHelper

class GetGroupsByUserRepository {
    private val service = RetrofitHelper.getRetrofitLeftBar()

    suspend fun getGroupsByUser(id: Int): Result<List<GroupDTO>> {
        return try {
            val response = service.getGroupsByUser(id)

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