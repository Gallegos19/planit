package com.example.planit.views.register.domain

import com.example.planit.views.register.data.model.CreateUserRequest
import com.example.planit.views.register.data.model.UserDTO
import com.example.planit.views.register.data.repository.RegisterRepository

class CreateUserUseCase {
    private val repository = RegisterRepository()

    suspend operator fun invoke(user: CreateUserRequest) : Result<UserDTO>{
        val result = repository.createUser(user)

        return result
    }
}