package com.example.planit.views.create_individual_activities.presentation

import CreateIndividualActivitiesViewModel
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.planit.components.Title
import com.example.planit.components.TopBar
import com.example.planit.components.left_bar.presentation.LeftBar
import com.example.planit.components.left_bar.presentation.LeftBarViewModel
import com.example.planit.core.data.SessionManager
import com.example.planit.views.create_individual_activities.data.model.CreateIndividualActivityDTO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CreateIndividualActivities(createIndividualActivitiesViewModel: CreateIndividualActivitiesViewModel,leftBarViewModel: LeftBarViewModel, navController: NavController, navigateToLogin: () -> Unit, navigationToIndividualActivity : () -> Unit, navigationToGeneralTeam : () -> Unit, navigationToCreateIndividualActivity : () -> Unit, navigationToHome : () -> Unit, navigationToAddTeam : () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            LeftBar(
                onClose = {
                    scope.launch { drawerState.close() }
                },
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route)
                },
                navigateToLogin,
                navigationToIndividualActivity,
                navigationToGeneralTeam,
                navigationToCreateIndividualActivity,
                navigationToHome,
                navigationToAddTeam,
                leftBarViewModel,
            )
        },
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    },
                    onProfileClick = { /* Navegar a perfil */ }
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title("Actividades")
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color.Black, thickness = 3.dp)
                Spacer(modifier = Modifier.height(60.dp))
                ActivityForm(createIndividualActivitiesViewModel)
            }
        }
    }
}

@Composable
fun ActivityForm(createIndividualActivitiesViewModel: CreateIndividualActivitiesViewModel) {
    val title by remember { mutableStateOf("") }
    val description by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var estimatedDate by remember { mutableStateOf(dateFormat.format(calendar.time)) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            estimatedDate = dateFormat.format(calendar.time)
            createIndividualActivitiesViewModel.updateDate(estimatedDate) // 🔹 Usar .value
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = createIndividualActivitiesViewModel.title.value,  // 🔹 Agregar .value
                onValueChange = { createIndividualActivitiesViewModel.updateTitle(it) },
                label = { Text("Título de la actividad") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = createIndividualActivitiesViewModel.description.value,  // 🔹 Agregar .value
                onValueChange = { createIndividualActivitiesViewModel.updateDescription(it) },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = createIndividualActivitiesViewModel.date.value,  // 🔹 Agregar .value
                    onValueChange = { },
                    label = { Text("Fecha Estimada") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { datePickerDialog.show() },
                    readOnly = true
                )
                Spacer(modifier = Modifier.width(20.dp))
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Seleccionar fecha",
                    tint = Color.Black,
                    modifier = Modifier.clickable { datePickerDialog.show() }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val userId = SessionManager.getUserId()
                    val activity = CreateIndividualActivityDTO(
                        user_id = userId,
                        title = createIndividualActivitiesViewModel.title.value,
                        category_id = createIndividualActivitiesViewModel.categoryId.value,
                        status = createIndividualActivitiesViewModel.status.value,
                        description = createIndividualActivitiesViewModel.description.value,
                        date = createIndividualActivitiesViewModel.date.value
                    )
                    createIndividualActivitiesViewModel.createIndividualActivity(activity)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar", color = Color.White)
                Spacer(modifier = Modifier.width(20.dp))
                Icon(imageVector = Icons.Default.Save, contentDescription = "Guardar", tint = Color.White)
            }
        }
    }
}
