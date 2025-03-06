package com.example.planit.views.general_team.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.core.data.GlobalStorage
import com.example.planit.core.data.SessionManager
import com.example.planit.views.general_team.data.model.UpdateGroup
import com.example.planit.views.general_team.domain.DeleteGroupUseCase
import com.example.planit.views.general_team.domain.GetGroupInfoUseCase
import com.example.planit.views.general_team.domain.UpdateGroupUseCase
import kotlinx.coroutines.launch

class GeneralTeamViewModel: ViewModel() {

    private val getGroupInfoUseCase = GetGroupInfoUseCase()
    private val updateGroupUseCase = UpdateGroupUseCase()
    private val deleteGroupUseCase = DeleteGroupUseCase()

    private val _title = mutableStateOf("")
    val title: String get() = _title.value

    private val _token = mutableStateOf("")
    val token: String get() = _token.value

    private val _description = mutableStateOf("")
    val description: String get() = _description.value

    private val _leader = mutableStateOf(false)
    val leader: Boolean get() = _leader.value

    private val _loading = mutableStateOf(false)
    val loading: Boolean get() = _loading.value

    private val _error = mutableStateOf("")
    val error: String get() = _error.value

    private val _onUpdateSuccess = mutableStateOf(false)
    val onUpdateSuccess: Boolean get() = _onUpdateSuccess.value

    private val _onDeleteSuccess = mutableStateOf(false)
    val onDeletedSuccess: Boolean get() = _onDeleteSuccess.value

    fun changeDescription(description: String) {
        _description.value = description
    }

    fun fetchGroupInfo(){
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = getGroupInfoUseCase.getGroupInfo(GlobalStorage.getGroupId()!!)
                result.onSuccess { groupInfo ->

                    _title.value = groupInfo.name
                    _token.value = groupInfo.token
                    _description.value = groupInfo.description
                    _leader.value = (groupInfo.user_id == SessionManager.getUserId())
                    _error.value = ""
                }.onFailure { exception ->
                    println("Error en petición interna  ${exception.message}")
                    _error.value = exception.message?: "Error desconocido"
                }
            } catch(e: Exception){
                println("Error en petición externa  ${e.message}")
                _error.value = e.message?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateGroup(id: Int, updateGroup: UpdateGroup) {
        _loading.value = true
        viewModelScope.launch {
            try {

                val result = updateGroupUseCase.updateGroup(id, updateGroup)
                result.onSuccess { message ->
                    _onUpdateSuccess.value = true
                }.onFailure { exception ->
                    _onUpdateSuccess.value = false
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deletedgroup(id: Int) {
        _loading.value = true
        viewModelScope.launch{
            try {
                val result = deleteGroupUseCase.deleteGroup(id)
                result.onSuccess {
                    _onDeleteSuccess.value = true
                }.onFailure { exception ->
                    _onDeleteSuccess.value = false
                }
            } catch(e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }


}