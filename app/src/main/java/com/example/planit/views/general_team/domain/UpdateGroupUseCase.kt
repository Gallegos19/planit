package com.example.planit.views.general_team.domain

import com.example.planit.views.general_team.data.model.UpdateGroupDTO
import com.example.planit.views.general_team.data.repository.UpdateGroupRepository


class UpdateGroupUseCase {
    private val repository = UpdateGroupRepository()

    suspend fun updateGroup(id: Int, updateGroup: UpdateGroupDTO): Result<String> {
        val result = this.repository.updateGroup(id, updateGroup)
        return result
    }
}