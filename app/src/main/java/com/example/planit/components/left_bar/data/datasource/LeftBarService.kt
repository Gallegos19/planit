package com.example.planit.components.left_bar.data.datasource

import com.example.planit.components.left_bar.data.model.ActivityUserDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface LeftBarService {

    @Headers("Content-Type: application/json")
    @GET("personal/activity/{id}")
    suspend fun getActivitiesUser(@Path("id") id: Int): Response<List<ActivityUserDTO>>

}