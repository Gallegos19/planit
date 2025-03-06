package com.example.planit.views.individual_activity.domain

import com.example.planit.views.individual_activity.data.repository.UpdateIndividualActivityRepository


class UpdateIndividualActivityUseCase {

    private val repository = UpdateIndividualActivityRepository()

    suspend fun updateIndividualActivity(id : Int) : Result<String>{
        val result = repository.updateIndividualActivityRepository(id)
        return result
    }
}