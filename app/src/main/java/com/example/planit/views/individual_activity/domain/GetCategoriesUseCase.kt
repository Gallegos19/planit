package com.example.planit.views.individual_activity.domain

import com.example.planit.views.individual_activity.data.model.CategoryDTO
import com.example.planit.views.individual_activity.data.model.IndividualActivityDTO
import com.example.planit.views.individual_activity.data.repository.GetCategoriesRepository

class GetCategoriesUseCase {

    private val repository = GetCategoriesRepository()

    suspend fun getCategories() : Result<List<CategoryDTO>>{
        val result = repository.getCategories()
        return result
    }
}