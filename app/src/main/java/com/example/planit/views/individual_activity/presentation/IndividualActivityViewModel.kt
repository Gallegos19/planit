package com.example.planit.views.individual_activity.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.core.data.GlobalStorage
import com.example.planit.core.data.SessionManager
import com.example.planit.views.individual_activity.domain.DeleteIndividualActivityUseCase
import com.example.planit.views.individual_activity.domain.UpdateIndividualActivityUseCase
import com.example.planit.views.individual_activity.domain.getIndividualActivityUseCase
import kotlinx.coroutines.launch

class IndividualActivityViewModel : ViewModel() {

    private val service = getIndividualActivityUseCase()
    private val delete = DeleteIndividualActivityUseCase()
    private val update = UpdateIndividualActivityUseCase()

    private val _loading = mutableStateOf(false)
    val loading: Boolean get() = _loading.value

    private val _error = mutableStateOf("")
    val error: String get() = _error.value

    private val _date = mutableStateOf("")
    val date: String get() = _date.value

    private val _description = mutableStateOf("")
    val description: String get() = _description.value

    private val _category = mutableStateOf("")
    val category: String get() = _category.value

    private val _title = mutableStateOf("")
    val title: String get() = _title.value

    private val _status = mutableStateOf("")
    val status: String get() = _status.value

    private val _message = mutableStateOf("")
    val message: String get() = _message.value

    private val _deleteSucccess = mutableStateOf(false)
    val deleteSuccess: Boolean get() = _deleteSucccess.value

    fun changeDate(date: String) {
        _date.value = date
    }

    fun changeDescription(description: String) {
        _description.value = description
    }

    fun changeStatus(status: String) {
        _status.value = status
    }

    fun changeCategory(category: String) {
        _category.value = category
    }

    fun fetchActivity() {
        _loading.value = true
        viewModelScope.launch {
            try {
                println("Iniciando peticion actividad individual")
                val result = service.getActivities(GlobalStorage.getIdActivity()?.toInt()!!)
                result.onSuccess { activity ->
                    _title.value = activity.title
                    _date.value = activity.date.toString()
                    _status.value = activity.status
                    _category.value = activity.category
                    _description.value = activity.description
                    _error.value = ""
                }.onFailure { exception ->
                    println("Error en fetchActivity: ${exception.message}")
                    _error.value = exception.message ?: "Error desconocido"
                }
            } catch (e: Exception) {
                println("Excepción capturada en fetchActivity: ${e.message}")
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteIndividualActivity(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = delete.deleteIndividualActivity(id)
                result.onSuccess {
                    GlobalStorage.saveUploadData(upload = true)
                    _message.value = "Actividad eliminado con éxito"
                    _deleteSucccess.value = true
                    println("Actividad eliminada con éxito, deleteSuccess: ${_deleteSucccess.value}")
                }.onFailure { exception ->
                    _error.value = exception.message ?: "Error al eliminar la actividad"
                    _deleteSucccess.value = false
                    println("Error al eliminar: ${_error.value}")
                }
            } finally {
                _loading.value = false
            }
        }
    }


}