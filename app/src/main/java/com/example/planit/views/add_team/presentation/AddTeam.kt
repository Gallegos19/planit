package com.example.planit.views.add_team.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planit.components.Title
import com.example.planit.components.TopBar
import com.example.planit.components.left_bar.presentation.LeftBar
import com.example.planit.components.left_bar.presentation.LeftBarViewModel
import com.example.planit.core.data.SessionManager
import com.example.planit.views.add_team.data.model.TeamDTO
import kotlinx.coroutines.launch


@Composable
fun AddTeam(addTeamViewModel: AddTeamViewModel, leftBarViewModel: LeftBarViewModel, navController: NavController, navigateToLogin: () -> Unit, navigationToIndividualActivity : () -> Unit, navigationToGeneralTeam : () -> Unit, navigationToCreateIndividualActivity : () -> Unit, navigationToHome : () -> Unit, navigationToAddTeam : () -> Unit) {
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
                Title("Unirse a Team")
                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = Color.Black, thickness = 3.dp)
                Spacer(modifier = Modifier.height(60.dp))
                ActivityForm(addTeamViewModel)
            }
        }
    }
}

@Composable
fun ActivityForm(addTeamViewModel: AddTeamViewModel) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = addTeamViewModel.token.value,  // 🔹 Agregar .value
                onValueChange = {
                    addTeamViewModel.token.value = it
                },
                label = { Text("Token de Team") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val userId = SessionManager.getUserId()
                    val team = TeamDTO(
                        user_id = userId,
                        token = addTeamViewModel.token.value,
                    )
                    addTeamViewModel.addTeam(team)
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