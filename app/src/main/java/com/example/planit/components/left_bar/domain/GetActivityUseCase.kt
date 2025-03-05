package com.example.planit.components.left_bar.domain

import com.example.planit.components.left_bar.data.model.ActivityUserDTO
import com.example.planit.components.left_bar.data.repository.GetActivityUserRepository

class GetActivityUseCase {
    private val repository = GetActivityUserRepository()

    suspend fun getActivities(id:Int) : Result<List<ActivityUserDTO>>{
        val result = repository.getActivities(id)
        return result
    }
}