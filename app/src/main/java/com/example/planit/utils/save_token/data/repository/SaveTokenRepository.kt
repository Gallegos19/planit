package com.example.planit.utils.save_token.data.repository

import com.example.planit.components.left_bar.data.model.ActivityUserDTO
import com.example.planit.core.network.RetrofitHelper
import com.example.planit.utils.save_token.data.datasource.SaveTokenService
import com.example.planit.utils.save_token.data.model.TokenDTO
import com.example.planit.views.create_individual_activities.data.model.CreateIndividualActivityDTO

class SaveTokenRepository {

    private val service = RetrofitHelper.getRetrofitToken()

    suspend fun saveToken(bodyToken: TokenDTO): Result<String> {
        return try {
            val response = service.saveToken(bodyToken)
            if (response.isSuccessful) {
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