package com.example.planit.views.create_individual_activities.data.datasource

import com.example.planit.views.create_individual_activities.data.model.CreateIndividualActivityDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateIndividualActivitiesService {

    @POST("personal/activity/")
    suspend fun createInvidivualActivity(@Body activity: CreateIndividualActivityDTO) : Response<String>
}