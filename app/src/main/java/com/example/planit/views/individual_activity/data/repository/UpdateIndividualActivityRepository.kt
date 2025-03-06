package com.example.planit.views.individual_activity.data.repository

import com.example.planit.core.network.RetrofitHelper

class UpdateIndividualActivityRepository {

    private val updateService = RetrofitHelper.getRetrofitIndividualActivity()

    suspend fun updateIndividualActivityRepository(id:Int): Result<String>{
        return try{
            val response = updateService.deleteIndividualActivity(id)
            if (response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Respuesta vacia por parte del servidor"))
            }else{
                Result.failure(Exception(response.errorBody()?.string()))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}