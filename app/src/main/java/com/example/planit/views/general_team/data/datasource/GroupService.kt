package com.example.planit.views.general_team.data.datasource


import com.example.planit.views.general_team.data.model.GroupDTO
import com.example.planit.views.general_team.data.model.UpdateGroup
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface GroupService {

    @GET("group/{id}")
    suspend fun getGroupInfo(@Path("id") groupId: Int): Response<GroupDTO>

    @PUT("group/{id}")
    suspend fun updateGroup(@Path("id") id: Int, @Body request: UpdateGroup) : Response<String>

    @DELETE("group/{id}")
    suspend fun deleteGroup(@Path("id") id: Int) : Response<String>
}