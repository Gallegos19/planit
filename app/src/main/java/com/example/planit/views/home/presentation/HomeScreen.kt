package com.example.planit.views.home.presentation

import com.example.planit.components.left_bar.presentation.LeftBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.planit.R
import com.example.planit.components.Line
import com.example.planit.components.Logo
import com.example.planit.components.TopBar
import com.example.planit.components.left_bar.presentation.LeftBarViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController,leftBarViewModel: LeftBarViewModel, navigateToLogin : () -> Unit, navigationToIndividualActivity : () -> Unit, navigationToGeneralTeam : () -> Unit, navigationToCreateIndividualActivity : () -> Unit, navigationToHome : () -> Unit, navigationToAddTeam : () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // Control de estado centralizado
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
        content = {
            Scaffold(
                topBar = {
                    TopBar(
                        onMenuClick = {
                            scope.launch { drawerState.open() }
                        },
                        onProfileClick = { /* Navegar a perfil */ }
                    )
                },
                content = { paddingValues ->
                    Column(modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Bienvenido a",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Logo()
                        ContentMessage()
                    }
                },
                containerColor = Color.White


            )
        }
    )
}

@Composable
fun ContentMessage() {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium) // Agrega sombra
            .clip(MaterialTheme.shapes.medium) // Forma redondeada
            .background(Color.White) // Fondo blanco
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Line()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Organiza, prioriza y optimiza tu flujo de trabajo como desarrollador de software con nuestra plataforma diseñada para agilizar tus proyectos.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.cohete),
                contentDescription = "Cohete",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
