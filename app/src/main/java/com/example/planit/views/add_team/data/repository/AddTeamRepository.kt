package com.example.planit.views.add_team.data.repository

import com.example.planit.core.network.RetrofitHelper
import com.example.planit.views.add_team.data.model.TeamDTO


class AddTeamRepository {

    private val addService = RetrofitHelper.getRetrofitAddTeam()

    suspend fun addTeamRepository(team: TeamDTO): Result<String>{
        println("team a agregarse \n" + team)
        return try{
            val response = addService.addTeam(team)
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