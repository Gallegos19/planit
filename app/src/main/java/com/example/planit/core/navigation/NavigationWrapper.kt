package com.example.planit.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planit.views.home.presentation.HomeScreen
import com.example.planit.views.individual_activity.presentation.IndividualActivity
import com.example.planit.views.login.presentation.LoginScreen
import com.example.planit.views.login.presentation.LoginViewModel
import com.example.planit.views.register.presentation.RegisterScreen
import com.example.planit.views.register.presentation.RegisterViewModel

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = IndividualActivity){
        composable<Login> {
            LoginScreen(
                loginViewModel = LoginViewModel(),
                navigateToHome = { navController.navigate(Home) },
                navigateToRegister = {navController.navigate(Register)}
            )
        }

        composable<Register> {
            RegisterScreen(
                registerViewModel = RegisterViewModel(),
                navigateToLogin = {navController.navigate(Login)}
            )
        }

        composable<Home> {
            HomeScreen(navController, navigateToLogin = {navController.navigate(Login)})
        }

        composable<IndividualActivity> {
            IndividualActivity(navController, navigateToLogin = {navController.navigate(Login)})
        }
    }
}