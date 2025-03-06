package com.example.planit.views.add_team.domain

import com.example.planit.views.add_team.data.model.TeamDTO
import com.example.planit.views.add_team.data.repository.AddTeamRepository


class AddTeamUseCase {
    private val repository = AddTeamRepository()

    suspend fun createIndividualActivity(team: TeamDTO) : Result<String>{
        val result = repository.addTeamRepository(team)
        return result
    }
}