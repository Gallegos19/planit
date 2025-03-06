package com.example.planit.views.general_team.data.repository

import com.example.planit.core.network.RetrofitHelper
import com.example.planit.views.general_team.data.model.UpdateGroupDTO

class UpdateGroupRepository {
    private val service = RetrofitHelper.getRetrofitGroup()

    suspend fun updateGroup(id: Int, updateGroup: UpdateGroupDTO): Result<String> {
        return try {
            val response = service.updateGroup(id, updateGroup);
            if(response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Respuesta vacia por parte del servidor"))
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}