package com.example.planit.views.watch_activity_team.presentation

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
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.planit.components.left_bar.presentation.LeftBarViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun WatchActivityTeam(navController: NavController, leftBarViewModel: LeftBarViewModel, navigateToLogin: () -> Unit, navigationToIndividualActivity : () -> Unit, navigationToGeneralTeam : () -> Unit, navigationToCreateIndividualActivity : () -> Unit, navigationToHome : () -> Unit, navigationToAddTeam : () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                navigationToCreateIndividualActivity,
                navigationToHome,
                navigationToAddTeam,
                leftBarViewModel
            )
        },
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    onMenuClick = { /* Abrir menú */ },
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
                Title("\"Actividad X\"")
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color.Black, thickness = 3.dp)
                Spacer(modifier = Modifier.height(20.dp))
                ActivityDetails()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityDetails() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd 'de' MMMM 'del' yyyy", Locale.getDefault())
    var estimatedDate by remember { mutableStateOf(dateFormat.format(calendar.time)) }
    val statusOptions = listOf("Redactado", "En Progreso", "Finalizado")
    var selectedStatus by remember { mutableStateOf(statusOptions[0]) }
    val employees = listOf("Diego Bejar", "María Pérez", "Juan López", "Ana Gómez")
    val responsibleList = remember { mutableStateListOf(employees[0]) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            estimatedDate = dateFormat.format(calendar.time)
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
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedStatus,
                    onValueChange = {},
                    label = { Text("Status") },
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    statusOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                selectedStatus = option
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = "Descripción de actividad",
                onValueChange = {},
                label = { Text("Descripción de actividad") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            responsibleList.forEachIndexed { index, responsible ->
                var expandedResp by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedResp,
                    onExpandedChange = { expandedResp = it }
                ) {
                    OutlinedTextField(
                        value = responsible,
                        onValueChange = {},
                        modifier = Modifier.weight(1f).menuAnchor(),
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedResp)
                        },
                        label = { Text("Responsable") }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedResp,
                        onDismissRequest = { expandedResp = false }
                    ) {
                        employees.forEach { employee ->
                            DropdownMenuItem(
                                text = { Text(employee) },
                                onClick = {
                                    responsibleList[index] = employee
                                    expandedResp = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar responsable",
                    tint = Color.Red,
                    modifier = Modifier.clickable { responsibleList.removeAt(index) }
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Agregar otro responsable",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { responsibleList.add(employees[0]) }
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    imageVector = Icons.Default.PersonAdd,
                    contentDescription = "Agregar responsable",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = estimatedDate,
                    onValueChange = {},
                    label = { Text("Fecha Estimada") },
                    modifier = Modifier
                        .weight(1f)
                        .clickable { datePickerDialog.show() },
                    readOnly = true
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Seleccionar fecha",
                    tint = Color.Black,
                    modifier = Modifier.clickable { datePickerDialog.show() }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { /* Guardar cambios */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar", color = Color.White)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Guardar")
            }
        }
    }
}
