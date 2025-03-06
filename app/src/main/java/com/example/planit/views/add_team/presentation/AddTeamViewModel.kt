package com.example.planit.views.add_team.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.core.data.GlobalStorage
import com.example.planit.views.add_team.data.model.TeamDTO
import com.example.planit.views.add_team.domain.AddTeamUseCase
import com.example.planit.views.create_individual_activities.data.model.CreateIndividualActivityDTO
import com.example.planit.views.create_individual_activities.domain.CreateIndividualActivityUseCase
import kotlinx.coroutines.launch

class AddTeamViewModel : ViewModel(){
    private val addActivityUse = AddTeamUseCase()

    var loading = mutableStateOf(false)
        private set

    var error = mutableStateOf("")
        private set

    var message = mutableStateOf("")
        private set

    var token = mutableStateOf("")
        private set

    var addTeam = mutableStateOf(false)
        private set


    fun addTeam(team: TeamDTO) {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = addActivityUse.createIndividualActivity(team)
                result.onSuccess {
                    GlobalStorage.saveUploadData(upload = true)
                    message.value = "Te uniste al grupo con éxito"
                    addTeam.value = true
                }.onFailure { exception ->
                    error.value = exception.message ?: "Error al crear la actividad"
                    addTeam.value = false
                }
            } finally {
                loading.value = false
            }
        }
    }
}
