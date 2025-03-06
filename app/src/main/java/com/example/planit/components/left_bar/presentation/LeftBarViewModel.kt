package com.example.planit.components.left_bar.presentation

import androidx.compose.runtime.mutableStateOf
import android.util.Log
import com.example.planit.components.left_bar.data.model.GroupDTO
import com.example.planit.components.left_bar.domain.GetGroupsByUserUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.components.left_bar.data.model.ActivityUserDTO
import com.example.planit.components.left_bar.domain.GetActivityUseCase
import com.example.planit.core.data.GlobalStorage
import com.example.planit.core.data.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LeftBarViewModel : ViewModel() {

    private val getActivitiesUseCase = GetActivityUseCase()
    private val getGroupsByUserUseCase = GetGroupsByUserUseCase()


    private val _activities = mutableStateOf<List<ActivityUserDTO>>(emptyList())
    val activities: List<ActivityUserDTO> get() = _activities.value

    private val _groups = mutableStateOf<List<GroupDTO>>(emptyList())
    val groups: List<GroupDTO> get() = _groups.value

    private val _loading = mutableStateOf(false)
    val loading: Boolean get() = _loading.value

    private val _error = mutableStateOf("")
    val error: String get() = _error.value

    private val _activityid = mutableStateOf(0)
    val activityid: Int get() = _activityid.value

    private val _groupId = mutableStateOf(0)
    val groupId: Int get() = _groupId.value

    private val _message = mutableStateOf("")
    val message: String get() = _message.value

    init {
        fetchActivities()
        fetchGroups()
        startUploadDataObserver()
    }

    fun changeActivityId(activityid: Int) {
        println("id actividad $activityid")
        _activityid.value = activityid
        GlobalStorage.saveIdActivity(activityid)
    }

    fun changeGroupId(groupId: Int) {
        println("id group ${groupId}")
        _groupId.value = groupId
        GlobalStorage.saveGroupId(groupId)
    }

    fun fetchActivities() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = getActivitiesUseCase.getActivities(SessionManager.getUserId())
                result.onSuccess { activitiesList ->
                    _activities.value = activitiesList
                    _error.value = ""
                }.onFailure { exception ->
                    _error.value = exception.message ?: "Error desconocido"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchGroups(){
        _loading.value = true
        println("fetchGroups: Entro al fetchGroups")
        viewModelScope.launch{
            try {
                val result = getGroupsByUserUseCase.getGroupsByUser(SessionManager.getUserId())
                result.onSuccess{ groups ->
                    println("fetchGroups: Se logro")
                    _groups.value = groups
                    _error.value = ""
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }

    private fun startUploadDataObserver() {
        viewModelScope.launch {
            while (true) {
                delay(2000)
                if (GlobalStorage.getUploadData()) {
                    fetchActivities() // Vuelve a obtener las actividades
                    GlobalStorage.saveUploadData(false)
                }
            }
        }
    }
}
