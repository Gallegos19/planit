package com.example.planit.views.create_individual_activities.data.repository

import com.example.planit.core.network.RetrofitHelper
import com.example.planit.views.create_individual_activities.data.model.CreateIndividualActivityDTO

class CreateIndividualActivityRepository {

    private val createService = RetrofitHelper.getRetrofitCreateIndividualActivity()

    suspend fun createIndividualActivityRepository(activity: CreateIndividualActivityDTO): Result<String>{
        println("Actividad a actualizar \n" + activity)
        return try{
            val response = createService.createInvidivualActivity(activity)
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