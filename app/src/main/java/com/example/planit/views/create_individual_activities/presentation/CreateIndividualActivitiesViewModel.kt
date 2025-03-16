import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planit.core.data.GlobalStorage
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivity
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivityInfo
import com.example.planit.core.data.local.personalActivity.repository.PersonalActivityInfoRepository
import com.example.planit.views.create_individual_activities.data.model.CreateIndividualActivityDTO
import com.example.planit.views.create_individual_activities.domain.CreateIndividualActivityUseCase
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class CreateIndividualActivitiesViewModel(private val repository: PersonalActivityInfoRepository) : ViewModel() {

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
                val info = PersonalActivityInfo(
                    categoryId = activity.category_id,
                    description = activity.description,
                    status = activity.status,
                    dateTo = activity.date
                )

                val activityWithInfo = PersonalActivity(
                    userId = activity.user_id,
                    activityId = 0,
                    title = activity.title
                )

                repository.insertPersonalActivityWithInfo(info, activityWithInfo)

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
