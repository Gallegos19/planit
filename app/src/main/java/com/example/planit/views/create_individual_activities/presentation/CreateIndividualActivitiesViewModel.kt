import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.core.data.GlobalStorage
import com.example.planit.views.create_individual_activities.data.model.CreateIndividualActivityDTO
import com.example.planit.views.create_individual_activities.domain.CreateIndividualActivityUseCase
import kotlinx.coroutines.launch

class CreateIndividualActivitiesViewModel : ViewModel() {

    private val createActivityUse = CreateIndividualActivityUseCase()

    var loading = mutableStateOf(false)
        private set

    var error = mutableStateOf("")
        private set

    var message = mutableStateOf("")
        private set

    var title = mutableStateOf("")
        private set

    var categoryId = mutableStateOf(2)
        private set

    var status = mutableStateOf("R")
        private set

    var description = mutableStateOf("")
        private set

    var date = mutableStateOf("")
        private set

    var createActivity = mutableStateOf(false)
        private set

    fun updateTitle(newTitle: String) {
        title.value = newTitle
    }

    fun updateDescription(newDescription: String) {
        description.value = newDescription
    }

    fun updateDate(newDate: String) {
        date.value = newDate
    }

    fun createIndividualActivity(activity: CreateIndividualActivityDTO) {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = createActivityUse.createIndividualActivity(activity)
                result.onSuccess {
                    GlobalStorage.saveUploadData(upload = true)
                    message.value = "Actividad creada con éxito"
                    createActivity.value = true
                }.onFailure { exception ->
                    error.value = exception.message ?: "Error al crear la actividad"
                    createActivity.value = false
                }
            } finally {
                loading.value = false
            }
        }
    }
}
