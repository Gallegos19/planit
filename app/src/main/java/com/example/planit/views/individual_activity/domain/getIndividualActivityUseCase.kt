package com.example.planit.views.individual_activity.domain

import com.example.planit.views.individual_activity.data.model.IndividualActivityDTO
import com.example.planit.views.individual_activity.data.repository.getIndividualActivityRepository

class getIndividualActivityUseCase() {

    private val repository = getIndividualActivityRepository()

    suspend fun getActivities(id:Int) : Result<IndividualActivityDTO>{
        val result = repository.getActivities(id)
        return result
    }
}