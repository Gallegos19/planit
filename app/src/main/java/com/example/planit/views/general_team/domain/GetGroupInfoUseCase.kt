package com.example.planit.views.general_team.domain


import com.example.planit.views.general_team.data.model.GroupDTO
import com.example.planit.views.general_team.data.repository.GetGroupInfoRepository

class GetGroupInfoUseCase {
    private val repository = GetGroupInfoRepository()

    suspend fun getGroupInfo(id: Int): Result<GroupDTO> {
        println("Use case $id")
        val result = this.repository.getGroupInfo(id)
        return result
    }

}