package com.example.planit.views.login.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.core.data.SessionManager
import com.example.planit.views.login.data.model.LoginUserRequest
import com.example.planit.views.login.data.model.UserDTO
import com.example.planit.views.login.data.repository.LoginRepository
import com.example.planit.views.login.domain.LoginUserUseCase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = LoginUserUseCase();
    private var _username = MutableLiveData<String>()
    val username : LiveData<String> = _username

    private var _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private var _success = MutableLiveData<Boolean>(false)
    val success : LiveData<Boolean> = _success

    private var _error = MutableLiveData<String>("")
    val error : LiveData<String> = _error

    private var _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    fun onClick(user: LoginUserRequest) {
        viewModelScope.launch {
            Log.d("LoginViewModel", "onClick iniciado con usuario: ${user.email} y contraseña ${user.password}")
            val result = repository.invoke(user)
            result.onSuccess { userDTO ->
                Log.d("LoginViewModel", "Login exitoso: Token recibido")
                _success.value = true
                _error.value = ""
                _token.value = userDTO.token
                SessionManager.saveToken(userDTO.token)
                SessionManager.saveUserId(userDTO.id)
                Log.d("LoginViewModel","Token: ${token.value}")
                Log.d("LoginViewModel","success: ${success.value}")

            }.onFailure { exception ->
                Log.e("LoginViewModel", "Login fallido: ${exception.message}")
                _success.value = false
                _error.value = exception.message ?: "Error desconocido"
            }
        }
    }

    fun saveToken(token: String){
        _token.value = token
    }

    fun onChangeUsername(username : String) {
        _username.value = username
    }

    fun onChangePassword (password : String) {
        _password.value = password
    }
}