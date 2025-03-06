package com.example.planit.views.individual_activity.domain

import com.example.planit.views.individual_activity.data.model.UpdateIndividualActivityDTO
import com.example.planit.views.individual_activity.data.repository.UpdateIndividualActivityRepository


class UpdateIndividualActivityUseCase {

    private val repository = UpdateIndividualActivityRepository()

    suspend fun updateIndividualActivity(id : Int, activity: UpdateIndividualActivityDTO) : Result<String>{
        val result = repository.updateIndividualActivityRepository(id, activity)
        return result
    }
}