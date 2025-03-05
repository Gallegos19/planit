package com.example.planit.views.login.data.datasource

import com.example.planit.views.login.data.model.LoginUserRequest
import com.example.planit.views.login.data.model.UserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("user/login")
    suspend fun LoginUser(@Body request: LoginUserRequest ) : Response<UserDTO>
}
