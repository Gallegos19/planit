package com.example.planit.views.individual_activity.presentation

import LeftBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.planit.R
import com.example.planit.components.Line
import com.example.planit.components.Title
import com.example.planit.components.TopBar
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun IndividualActivity(navController: NavController, navigateToLogin: () -> Unit) {
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
                navigateToLogin
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
                        Title("Actividad")
                        Spacer(modifier = Modifier.height(20.dp))
                        Line()
                        Spacer(modifier = Modifier.height(20.dp))
                        ContentForm()
                    }
                },
                containerColor = Color.White
            )
        }
    )
}

@Composable
fun ContentForm() {
    var selectedDate by remember { mutableStateOf("27 de Febrero del 2025") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            selectedDate = "$dayOfMonth de ${getMonthName(month)} del $year"
        },
        calendar.get(Calendar.YEAR ),
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
                value = selectedDate,
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                shape = RoundedCornerShape(10.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.Black, // Asegura que el label se vea bien
                    unfocusedLabelColor = Color.Black
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
                onValueChange = { description = it },
                placeholder = { Text("Escribe la descripción de la actividad") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedPlaceholderColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Black
                ),
                singleLine = false,
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Botón Guardar
            Button(
                onClick = { /* Guardar lógica aquí */ },
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
                onClick = { /* Eliminar lógica aquí */ },
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

// Función para obtener el nombre del mes en español
fun getMonthName(month: Int): String {
    return listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )[month]
}
