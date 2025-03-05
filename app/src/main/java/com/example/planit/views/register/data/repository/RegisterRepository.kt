package com.example.planit.views.register.data.repository

import com.example.planit.core.network.RetrofitHelper
import com.example.planit.views.register.data.model.CreateUserRequest
import com.example.planit.views.register.data.model.UserDTO

class RegisterRepository() {

    private val registerService = RetrofitHelper.getRetrogitRegister()

    suspend fun createUser(request: CreateUserRequest) : Result<UserDTO> {
        return try {
            val response = registerService.createUser(request)
            if (response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception(response.errorBody()?.string()))
            }
        }catch (e : Exception){
            Result.failure(e)
        }
    }
}