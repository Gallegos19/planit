package com.example.planit.views.individual_activity.domain

import com.example.planit.views.individual_activity.data.repository.DeleteIndividualActivityRepository

class DeleteIndividualActivityUseCase {

    private val repository = DeleteIndividualActivityRepository()

    suspend fun deleteIndividualActivity(id : Int) : Result<String>{
        val result = repository.deleteIndividualActivityRepository(id)
        return result
    }
}