package com.example.planit.views.individual_activity.presentation

import com.example.planit.components.left_bar.presentation.LeftBar
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.planit.components.Line
import com.example.planit.components.Title
import com.example.planit.components.TopBar
import com.example.planit.components.left_bar.presentation.LeftBarViewModel
import com.example.planit.core.data.GlobalStorage
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun IndividualActivity(
    individualActivityViewModel: IndividualActivityViewModel,
    navController: NavController,
    leftBarViewModel: LeftBarViewModel,
    navigateToLogin: () -> Unit,
    navigationToIndividualActivity: () -> Unit,
    navigationToGeneralTeam: () -> Unit,
    navigateToHome: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val title by remember { derivedStateOf { individualActivityViewModel.title } }

    // Ejecuta fetchActivity() cuando la pantalla se cargue
    LaunchedEffect(Unit) {
        individualActivityViewModel.fetchActivity()
    }

    // Escuchar cambios en deleteSuccess para redirigir a Home
    LaunchedEffect(individualActivityViewModel.deleteSuccess) {
        if (individualActivityViewModel.deleteSuccess) {
            navigateToHome() // Redirigir cuando la actividad se elimine con éxito
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            LeftBar(
                onClose = { scope.launch { drawerState.close() } },
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route)
                },
                navigateToLogin,
                navigationToIndividualActivity,
                navigationToGeneralTeam,
                leftBarViewModel
            )
        },
        content = {
            Scaffold(
                topBar = {
                    TopBar(
                        onMenuClick = { scope.launch { drawerState.open() } },
                        onProfileClick = { /* Navegar a perfil */ }
                    )
                },
                content = { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Title(title)
                        Spacer(modifier = Modifier.height(20.dp))
                        Line()
                        Spacer(modifier = Modifier.height(20.dp))
                        ContentForm(individualActivityViewModel)
                    }
                },
                containerColor = Color.White
            )
        }
    )
}


@Composable
fun ContentForm(individualActivityViewModel: IndividualActivityViewModel) {
    var date by remember { mutableStateOf(individualActivityViewModel.date) }
    var description by remember { mutableStateOf(individualActivityViewModel.description) }
    var category by remember { mutableStateOf(individualActivityViewModel.category) }

// Mantiene la sincronización sin perder los datos al escribir
    val updatedDate by rememberUpdatedState(individualActivityViewModel.date)
    val updatedDescription by rememberUpdatedState(individualActivityViewModel.description)
    val updatedCategory by rememberUpdatedState(individualActivityViewModel.category)

// Actualiza los valores cuando cambian en el ViewModel, pero solo si el usuario no los ha editado manualmente
    LaunchedEffect(updatedDate, updatedDescription, updatedCategory) {
        if (date.isBlank()) date = updatedDate
        if (description.isBlank()) description = updatedDescription
        if (category.isBlank()) category = updatedCategory
    }


    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val newDate = "$dayOfMonth de ${getMonthName(month)} del $year"
            individualActivityViewModel.changeDate(newDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = Modifier
            .padding(20.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Fecha de recordatorio
            Text(
                text = "Fecha de Recordatorio",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = date,
                onValueChange = { newValue ->
                    date = newValue
                    individualActivityViewModel.changeDate(newValue)
                },
                placeholder = { Text("Selecciona una fecha") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Seleccionar fecha",
                        tint = Color.Black
                    )
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Descripción
            Text(
                text = "Descripción",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { newValue ->
                    description = newValue
                    individualActivityViewModel.changeDescription(newValue)
                },
                placeholder = { Text("Escribe la descripción de la actividad") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                singleLine = false,
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Categoría
            Text(
                text = "Categoría",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { newValue ->
                    category = newValue
                    individualActivityViewModel.changeCategory(newValue)
                },
                placeholder = { Text("Escribe la categoría de la actividad") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                singleLine = false,
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Botón Guardar
            Button(
                onClick = { /* Lógica para guardar */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Guardar", color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.Save, contentDescription = "Guardar", tint = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Line()

            Spacer(modifier = Modifier.height(20.dp))

            // Botón Eliminar
            Button(
                onClick = {
                    val activityId = GlobalStorage.getIdActivity()
                    if (activityId != null) {
                        individualActivityViewModel.deleteIndividualActivity(activityId)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Eliminar", color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

fun getMonthName(month: Int): String {
    return listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )[month]
}