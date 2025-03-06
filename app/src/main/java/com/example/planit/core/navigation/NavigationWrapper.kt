package com.example.planit.core.navigation

import CreateIndividualActivitiesViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planit.components.left_bar.presentation.LeftBarViewModel
import com.example.planit.views.add_team.presentation.AddTeam
import com.example.planit.views.add_team.presentation.AddTeamViewModel
import com.example.planit.views.create_activities_team.presentation.CreateActivitiesTeam
import com.example.planit.views.create_individual_activities.presentation.CreateIndividualActivities
import com.example.planit.views.general_team.presentation.GeneralTeam
import com.example.planit.views.general_team.presentation.GeneralTeamViewModel
import com.example.planit.views.home.presentation.HomeScreen
import com.example.planit.views.individual_activity.presentation.IndividualActivity
import com.example.planit.views.individual_activity.presentation.IndividualActivityViewModel
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
    val leftBarViewModel= LeftBarViewModel()

    NavHost(navController = navController, startDestination = Login){
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
            HomeScreen(navController, leftBarViewModel, navigateToLogin = {navController.navigate(Login)}, navigationToIndividualActivity = {navController.navigate(IndividualActivity)}, navigationToGeneralTeam = {navController.navigate(GeneralTeam)}, navigationToCreateIndividualActivity = {navController.navigate(CreateIndividualActivities)}, navigationToHome = {navController.navigate(Home)}, navigationToAddTeam = {navController.navigate(AddTeam)})
        }

        composable<IndividualActivity> {
            IndividualActivity(IndividualActivityViewModel(), navController, leftBarViewModel, navigateToLogin = {navController.navigate(Login)}, navigationToIndividualActivity = {navController.navigate(IndividualActivity)}, navigationToGeneralTeam = {navController.navigate(GeneralTeam)}, navigationToAddTeam = {navController.navigate(AddTeam)} ,navigateToHome = {navController.navigate(Home)}, navigationToCreateIndividualActivity = {navController.navigate(CreateIndividualActivities)})
        }

        composable<GeneralTeam> {
            GeneralTeam(GeneralTeamViewModel(), navController, leftBarViewModel,navigateToLogin = {navController.navigate(Login)}, navigationToIndividualActivity = {navController.navigate(IndividualActivity)}, navigationToGeneralTeam = {navController.navigate(GeneralTeam)}, navigationToCreateIndividualActivity = {navController.navigate(CreateIndividualActivities)}, navigationToHome = {navController.navigate(Home)}, navigationToAddTeam = {navController.navigate(AddTeam)})
        }

        composable<ListActivitiesTeam> {
            ListActivitiesTeam(navController, leftBarViewModel, navigateToLogin = {navController.navigate(Login)}, navigationToIndividualActivity = {navController.navigate(IndividualActivity)}, navigationToGeneralTeam = {navController.navigate(GeneralTeam)}, navigationToCreateIndividualActivity = {navController.navigate(CreateIndividualActivities)}, navigationToHome = {navController.navigate(Home)}, navigationToAddTeam = {navController.navigate(AddTeam)})
        }
        composable<MembersTeam> {
            MembersTeam (navController, leftBarViewModel ,navigateToLogin = {navController.navigate(Login)}, navigationToIndividualActivity = {navController.navigate(IndividualActivity)}, navigationToGeneralTeam = {navController.navigate(GeneralTeam)}, navigationToCreateIndividualActivity = {navController.navigate(CreateIndividualActivities)}, navigationToHome = {navController.navigate(Home)}, navigationToAddTeam = {navController.navigate(AddTeam)})
        }

        composable<CreateActivitiesTeam> {
            CreateActivitiesTeam(leftBarViewModel,navController, navigateToLogin = {navController.navigate(Login)}, navigationToIndividualActivity = {navController.navigate(IndividualActivity)}, navigationToGeneralTeam = {navController.navigate(GeneralTeam)}, navigationToCreateIndividualActivity = {navController.navigate(CreateIndividualActivities)}, navigationToHome = {navController.navigate(Home)}, navigationToAddTeam = {navController.navigate(AddTeam)})
        }

        composable<WatchActivityTeam> {
            WatchActivityTeam(navController, leftBarViewModel ,navigateToLogin = {navController.navigate(Login)}, navigationToIndividualActivity = {navController.navigate(IndividualActivity)}, navigationToGeneralTeam = {navController.navigate(GeneralTeam)}, navigationToCreateIndividualActivity = {navController.navigate(CreateIndividualActivities)}, navigationToHome = {navController.navigate(Home)}, navigationToAddTeam = {navController.navigate(AddTeam)})
        }

        composable<CreateIndividualActivities> {
            CreateIndividualActivities (createIndividualActivitiesViewModel = CreateIndividualActivitiesViewModel(),leftBarViewModel,navController, navigateToLogin = {navController.navigate(Login)}, navigationToIndividualActivity = {navController.navigate(IndividualActivity)}, navigationToGeneralTeam = {navController.navigate(GeneralTeam)}, navigationToCreateIndividualActivity = {navController.navigate(CreateIndividualActivities)}, navigationToHome = {navController.navigate(Home)}, navigationToAddTeam = {navController.navigate(AddTeam)})
        }

        composable<AddTeam> {
            AddTeam(addTeamViewModel = AddTeamViewModel(),leftBarViewModel,navController, navigateToLogin = {navController.navigate(Login)}, navigationToIndividualActivity = {navController.navigate(IndividualActivity)}, navigationToGeneralTeam = {navController.navigate(GeneralTeam)}, navigationToCreateIndividualActivity = {navController.navigate(CreateIndividualActivities)}, navigationToHome = {navController.navigate(Home)}, navigationToAddTeam = {navController.navigate(AddTeam)})
        }
    }
}