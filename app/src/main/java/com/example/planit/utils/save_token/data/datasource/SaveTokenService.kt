package com.example.planit.utils.save_token.data.datasource

import com.example.planit.utils.save_token.data.model.TokenDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface SaveTokenService {

    @POST("user/token")
    suspend fun saveToken(@Body bodyToken: TokenDTO) : Response<String>
}