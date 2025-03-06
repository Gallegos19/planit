package com.example.planit.components.left_bar.domain

import com.example.planit.components.left_bar.data.model.GroupDTO
import com.example.planit.components.left_bar.data.repository.GetGroupsByUserRepository

class GetGroupsByUserUseCase {
    private val repository = GetGroupsByUserRepository()

    suspend fun getGroupsByUser(id: Int): Result<List<GroupDTO>> {
        val result = this.repository.getGroupsByUser((id))
        return result
    }
}