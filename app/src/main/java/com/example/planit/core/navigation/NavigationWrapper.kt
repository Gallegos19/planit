package com.example.planit.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planit.views.create_activities_team.presentation.CreateActivitiesTeam
import com.example.planit.views.general_team.presentation.GeneralTeam
import com.example.planit.views.home.presentation.HomeScreen
import com.example.planit.views.individual_activity.presentation.IndividualActivity
import com.example.planit.views.list_activities_team.presentation.ListActivitiesTeam
import com.example.planit.views.login.presentation.LoginScreen
import com.example.planit.views.login.presentation.LoginViewModel
import com.example.planit.views.members_team.presentation.MembersTeam
import com.example.planit.views.register.presentation.RegisterScreen
import com.example.planit.views.register.presentation.RegisterViewModel
import com.example.planit.views.watch_activity_team.presentation.WatchActivityTeam

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WatchActivityTeam){
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

        composable<GeneralTeam> {
            GeneralTeam(navController, navigateToLogin = {navController.navigate(Login)})
        }

        composable<ListActivitiesTeam> {
            ListActivitiesTeam(navController, navigateToLogin = {navController.navigate(Login)})
        }
        composable<MembersTeam> {
            MembersTeam (navController, navigateToLogin = {navController.navigate(Login)})
        }

        composable<CreateActivitiesTeam> {
            CreateActivitiesTeam()
        }
        composable<WatchActivityTeam> {
            WatchActivityTeam()
        }
    }
}