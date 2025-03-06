package com.example.planit.views.add_team.data.datasource

import com.example.planit.views.add_team.data.model.TeamDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddTeamService {

    @POST("/group/user/")
    suspend fun addTeam(@Body team:TeamDTO) : Response<String>
}