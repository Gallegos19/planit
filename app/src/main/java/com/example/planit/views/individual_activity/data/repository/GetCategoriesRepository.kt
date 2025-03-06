package com.example.planit.views.individual_activity.data.repository

import com.example.planit.core.network.RetrofitHelper
import com.example.planit.views.individual_activity.data.model.CategoryDTO

class GetCategoriesRepository {

    private val getCategoryService = RetrofitHelper.getRetrofitIndividualActivity()

    suspend fun getCategories() : Result<List<CategoryDTO>>{
        return try {
            val response = getCategoryService.getCategories()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }

        }catch (e: Exception){
            Result.failure(e)
        }
    }
}