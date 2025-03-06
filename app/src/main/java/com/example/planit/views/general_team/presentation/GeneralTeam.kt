package com.example.planit.views.general_team.presentation

import com.example.planit.components.left_bar.presentation.LeftBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Save
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
import com.example.planit.components.left_bar.presentation.LeftBarViewModel
import com.example.planit.core.data.GlobalStorage
import com.example.planit.views.general_team.data.model.UpdateGroup
import kotlinx.coroutines.launch

@Composable
fun GeneralTeam(generalTeamViewModel: GeneralTeamViewModel, navController: NavController,leftBarViewModel: LeftBarViewModel, navigateToLogin: () -> Unit, navigationToIndividualActivity : () -> Unit, navigationToGeneralTeam : () -> Unit, navigateToHome : () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val title by remember { derivedStateOf { generalTeamViewModel.title } }

    LaunchedEffect(Unit){
        generalTeamViewModel.fetchGroupInfo();
    }

    LaunchedEffect(generalTeamViewModel.onUpdateSuccess) {
        if (generalTeamViewModel.onUpdateSuccess) {
            navigateToHome()
        }
    }

    LaunchedEffect(generalTeamViewModel.onDeletedSuccess) {
        if (generalTeamViewModel.onDeletedSuccess) {
            navigateToHome()
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
                Title("Equipo")
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Line()
                Spacer(modifier = Modifier.height(20.dp))
                ContentForm(generalTeamViewModel, title)
            }
        }
    }
}

@Composable
fun ContentForm(generalTeamViewModel: GeneralTeamViewModel, title: String) {
    var codeAccess by remember { mutableStateOf(generalTeamViewModel.token) }
    var description by remember { mutableStateOf(generalTeamViewModel.description) }
    var leader by remember { mutableStateOf(generalTeamViewModel.leader) }

    val updatedDescription by rememberUpdatedState(generalTeamViewModel.description)
    val updateToken by rememberUpdatedState(generalTeamViewModel.token)
    val updateLeader by remember { mutableStateOf(generalTeamViewModel.leader) }
    // Sincroniza valores iniciales sin sobrescribir cambios del usuario
    LaunchedEffect(generalTeamViewModel.description) {
        description = generalTeamViewModel.description.takeIf { it.isNotBlank() } ?: description
        codeAccess = generalTeamViewModel.token.takeIf { it.isNotBlank()} ?: codeAccess
        leader = generalTeamViewModel.leader.takeIf { it } ?: leader
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Código de acceso",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = codeAccess,
                    onValueChange = { codeAccess = it },
                    enabled = false,
                    modifier = Modifier
                        .width(180.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = Color.White,
                        disabledTextColor = Color.Black
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Descripción",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { newValue ->
                        description = newValue
                        generalTeamViewModel.changeDescription(newValue)
                    },
                    placeholder = { Text("Escribe la descripción de la actividad") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        disabledTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedPlaceholderColor = Color.Black,
                        unfocusedPlaceholderColor = Color.Black
                    ),
                    singleLine = false,
                    maxLines = 2,
                    enabled = leader
                )


                Spacer(modifier = Modifier.height(20.dp))

                NavigationButton(text = "Actividades", icon = Icons.Default.KeyboardArrowRight)
                Spacer(modifier = Modifier.height(10.dp))
                NavigationButton(text = "Miembros", icon = Icons.Default.Group)
                Spacer(modifier = Modifier.height(20.dp))

                if(leader) {
                    Button(
                        onClick = {
                            val updateGroup = UpdateGroup(
                                name = title,
                                description = description
                            )
                            val groupId = GlobalStorage.getGroupId()
                            if (groupId != null) {
                                generalTeamViewModel.updateGroup(groupId, updateGroup);
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text("Guardar", color = Color.White, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.Default.Save, contentDescription = "Guardar", tint = Color.White)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Line()
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val groupId = GlobalStorage.getGroupId()
                            if (groupId != null) {
                                generalTeamViewModel.deletedgroup(groupId);
                            } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text("Eliminar", color = Color.White, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun NavigationButton(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Button(
        onClick = { /* Guardar lógica aquí */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(Color.White),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text, color = Color.Black, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(imageVector = icon, contentDescription = text, tint = Color.Black)
    }
}
