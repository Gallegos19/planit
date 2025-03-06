package com.example.planit.components.left_bar.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.planit.R
import com.example.planit.components.left_bar.data.model.ActivityUserDTO
import com.example.planit.components.left_bar.data.model.GroupDTO
import kotlinx.coroutines.launch

@Composable
fun LeftBar(
    onClose: () -> Unit,
    onNavigate: (String) -> Unit,
    navigateToRegister: () -> Unit,
    navigationToIndividualActivity: () -> Unit,
    navigationToGeneralTeam: () -> Unit,
    leftBarViewModel: LeftBarViewModel,
) {
    val scope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Estado del ViewModel
    val activities by remember { derivedStateOf { leftBarViewModel.activities } }
    val groups by remember { derivedStateOf { leftBarViewModel.groups }}
    val loading by remember { derivedStateOf { leftBarViewModel.loading } }
    val error by remember { derivedStateOf { leftBarViewModel.error } }

    Column(
        modifier = Modifier
            .width(screenWidth * 0.75f)
            .fillMaxHeight()
            .background(Color.White)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.planit_logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            DrawerItem("Inicio", Icons.Default.Home) {
                scope.launch { onClose() }
                onNavigate("home")
            }
            DrawerItem("Tu Espacio", Icons.Default.Add) {
                scope.launch { onClose() }
                onNavigate("yourSpace")
            }

            // Mostrar lista de actividades
            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                error.isNotEmpty() -> {
                    Text(
                        text = "Error: $error",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                activities.isNotEmpty() -> {
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        activities.forEach { activity ->
                            ActivityItem(activity) {
                                scope.launch {
                                    onClose() // Cierra la barra antes de navegar
                                    leftBarViewModel.changeActivityId(activity.activity_id)
                                    navigationToIndividualActivity()
                                }
                            }
                        }
                    }
                }
                else -> {
                    Text(
                        text = "No tienes actividades registradas",
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            DrawerItem("Espacios de Equipo", Icons.Default.Group) {
                scope.launch { onClose() }
                onNavigate("teamSpaces")
            }

            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                error.isNotEmpty() -> {
                    Text(
                        text = "Error: $error",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                groups.isNotEmpty() -> {
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        groups.forEach { group ->
                            GroupItem(group) {
                                scope.launch {
                                    onClose() // Cierra la barra antes de navegar
                                    leftBarViewModel.changeGroupId(group.id)
                                    navigationToGeneralTeam()
                                }
                            }
                        }
                    }
                }
                else -> {
                    Text(
                        text = "No tienes grupos registrados",
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onClose() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            logout(navigateToRegister)
        }
    }
}

@Composable
fun ActivityItem(activity: ActivityUserDTO, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .clickable { onClick() }  // Hace que la actividad sea seleccionable
            .padding(8.dp)
    ) {
        Text(text = activity.title, color = Color.Black, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun GroupItem(group: GroupDTO, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .clickable { onClick() }  // Hace que el grupo sea seleccionable
            .padding(8.dp)
    ) {
        Text(text = group.name, color = Color.Black, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun logout(navigateToLogin: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToLogin() },
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Cerrar Sesión", color = Color.White)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(imageVector = Icons.Default.Logout, contentDescription = "Cerrar Sesión", tint = Color.White)
    }
}

@Composable
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Black)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.Black)
    }
}
