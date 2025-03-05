package com.example.planit.views.login.domain

import com.example.planit.views.login.data.model.LoginUserRequest
import com.example.planit.views.login.data.model.UserDTO
import com.example.planit.views.login.data.repository.LoginRepository

class LoginUserUseCase() {
    private val repository = LoginRepository()

    suspend operator fun invoke(user: LoginUserRequest): Result<UserDTO> {
        return repository.LoginUser(user)
    }

}