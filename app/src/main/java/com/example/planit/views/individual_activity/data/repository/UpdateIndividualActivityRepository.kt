package com.example.planit.views.individual_activity.data.repository

import com.example.planit.core.network.RetrofitHelper
import com.example.planit.views.individual_activity.data.model.UpdateIndividualActivityDTO

class UpdateIndividualActivityRepository {

    private val updateService = RetrofitHelper.getRetrofitIndividualActivity()

    suspend fun updateIndividualActivityRepository(id:Int, activity: UpdateIndividualActivityDTO): Result<String>{
        println("Actividad \n"+ id  +" a actualizar \n" + activity)
        return try{
            val response = updateService.updateInvidivualActivity(id,activity)
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