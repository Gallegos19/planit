package com.example.planit.views.individual_activity.data.datasource

import com.example.planit.views.individual_activity.data.model.IndividualActivityDTO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path

interface IndividualActivityService {

    @Headers("Content-Type: application/json")
    @GET("personal/activity/info/{id}")
    suspend fun getIndividualActivity(@Path("id") id: Int): Response<IndividualActivityDTO>

    @PUT("personal/activity/{id}")
    suspend fun updateInvidivualActivity(@Path("id") id: Int) : Response<String>

    @DELETE("personal/activity/{id}")
    suspend fun deleteIndividualActivity(@Path("id") id: Int) : Response<String>


}