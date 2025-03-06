package com.example.planit.views.create_individual_activities.domain

import com.example.planit.views.create_individual_activities.data.model.CreateIndividualActivityDTO
import com.example.planit.views.create_individual_activities.data.repository.CreateIndividualActivityRepository
import com.example.planit.views.individual_activity.data.model.UpdateIndividualActivityDTO

class CreateIndividualActivityUseCase {

    private val repository = CreateIndividualActivityRepository()

    suspend fun createIndividualActivity(activity: CreateIndividualActivityDTO) : Result<String>{
        val result = repository.createIndividualActivityRepository(activity)
        return result
    }
}