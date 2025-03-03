package com.example.planit.views.list_activities_team.presentation

import LeftBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.planit.components.Line
import com.example.planit.components.Title
import com.example.planit.components.TopBar
import kotlinx.coroutines.launch

@Composable
fun ListActivitiesTeam(navController: NavController, navigateToLogin: () -> Unit) {
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
                navigateToLogin
            )
        },
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
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
                TopSection()
                Spacer(modifier = Modifier.height(10.dp))
                TabsSection()
                Spacer(modifier = Modifier.height(20.dp))
                ActivitiesList()
            }
        }
    }
}

@Composable
fun TopSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Title("Actividades")
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "Agregar",
            tint = Color.Black
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
    Divider(color = Color.Black, thickness = 3.dp)
}

@Composable
fun TabsSection() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Redactado", "Progreso", "Finalizado")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabTitles.forEachIndexed { index, title ->
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier
                    .clickable { selectedTab = index }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun ActivitiesList() {
    val activities = listOf(
        "Título de la actividad" to "Persona asignada",
        "Título de la actividad" to "Persona asignada",
        "Título de la actividad" to "Persona asignada",
        "Título de la actividad" to "Persona asignada"
    )

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(activities) { activity ->
            ActivityItem(title = activity.first, assignedPerson = activity.second)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ActivityItem(title: String, assignedPerson: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                Text(text = assignedPerson, fontSize = 14.sp, color = Color.Gray)
            }
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar",
                tint = Color.Red,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
