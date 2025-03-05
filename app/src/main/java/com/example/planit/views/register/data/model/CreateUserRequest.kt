package com.example.planit.views.register.data.model

import android.provider.ContactsContract.CommonDataKinds.Email
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest (
    val firstname : String,
    val lastname : String,
    val email : String,
    val password : String
)