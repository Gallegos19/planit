package com.example.planit.views.general_team.domain

import com.example.planit.views.general_team.data.repository.DeleteGroupRepository

class DeleteGroupUseCase {
    private val repository = DeleteGroupRepository()

    suspend fun deleteGroup(id: Int): Result<String> {
        val result = this.repository.deleteGroup(id)
        return result
    }
}