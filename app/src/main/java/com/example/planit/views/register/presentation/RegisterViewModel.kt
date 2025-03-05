package com.example.planit.views.register.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.views.register.data.model.CreateUserRequest
import com.example.planit.views.register.domain.CreateUserUseCase
import kotlinx.coroutines.launch

class RegisterViewModel() : ViewModel() {

    private val repository = CreateUserUseCase()

    private var _nombre = MutableLiveData<String>()
    val nombre : LiveData<String> = _nombre

    private var _apellido = MutableLiveData<String>()
    val apellido : LiveData<String> = _apellido

    private var _correo = MutableLiveData<String>()
    val correo : LiveData<String> = _correo

    private var _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private var _success = MutableLiveData<Boolean>(false)
    val success : LiveData<Boolean> = _success

    private var _error = MutableLiveData<String>("")
    val error : LiveData<String> = _error

    fun onChangeNombre(nombre : String) {
        _nombre.value = nombre
    }

    fun onChangeApellido(apellido : String) {
        _apellido.value = apellido
    }

    fun onChangeCorreo(correo : String) {
        _correo.value = correo
    }

    fun onChangePassword (password : String) {
        _password.value = password
    }

    fun onClick(user: CreateUserRequest) {
        viewModelScope.launch {
            Log.d("RegisterViewModel", "onClick iniciado con usuario: ${user.email}")
            try {
                val result = repository.invoke(user)
                result.onSuccess { userDTO ->
                    Log.d("RegisterViewModel", "Registro exitoso:")
                    _success.value = true
                    _error.value = ""
                }.onFailure { exception ->
                    Log.e("RegisterViewModel", "Registro fallido: ${exception.message}")
                    _success.value = false
                    _error.value = exception.message ?: "Error desconocido"
                }
            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Excepción: ${e.message}")
                _success.value = false
                _error.value = e.message ?: "Error al intentar realizar la operación"
            }
        }
    }
}