package com.example.planit.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planit.views.login.presentation.loginScreen
import com.example.planit.views.register.presentation.registerScreen
import com.example.planit.core.navigation.Login
import com.example.planit.core.navigation.Home
import com.example.planit.core.navigation.Register
import com.example.planit.views.home.presentation.HomeScreen

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home){
        composable<Login> {
            loginScreen()
        }

        composable<Register> {
            registerScreen()
        }

        composable<Home> {
            HomeScreen(navController)
        }
    }
}