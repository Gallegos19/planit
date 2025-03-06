package com.example.planit.views.general_team.data.repository

import com.example.planit.core.network.RetrofitHelper
import com.example.planit.views.general_team.data.model.GroupDTO

class GetGroupInfoRepository {

    private val service = RetrofitHelper.getRetrofitGroup()

    suspend fun getGroupInfo(id: Int): Result<GroupDTO> {
        return try {

            println("Repository $id")

            val response = service.getGroupInfo(id)

            if(response.isSuccessful) {
                Result.success(response.body()!!)
            }else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }}