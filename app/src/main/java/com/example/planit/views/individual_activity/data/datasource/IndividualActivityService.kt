package com.example.planit.views.individual_activity.data.datasource

import com.example.planit.views.individual_activity.data.model.IndividualActivityDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IndividualActivityService {

    @GET("personal/activity/info/")
    suspend fun getIndividualActivity(@Path("id") id: Int): Response<IndividualActivityDTO>


}