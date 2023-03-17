package com.masterplus.mesnevi.features.app.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.masterplus.mesnevi.features.app.domain.model.kBottomNavRouteNames
import com.masterplus.mesnevi.features.app.presentation.navigation.AppNavHost
import com.masterplus.mesnevi.features.app.presentation.navigation.BottomBarVisibilityViewModel
import com.masterplus.mesnevi.features.app.presentation.navigation.NavigationBar

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun MyApp(
    navController: NavHostController = rememberNavController(),
    bottomBarVisibilityViewModel: BottomBarVisibilityViewModel = hiltViewModel()
){

    LaunchedEffect(true){
        navController.addOnDestinationChangedListener { controller, navDestination, bundle ->
            val isBottomItemsVisible = kBottomNavRouteNames.contains(navDestination.route)
            bottomBarVisibilityViewModel.setVisibility(isBottomItemsVisible)
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                bottomBarVisibilityViewModel.isVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ){
                NavigationBar(navController)
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        AppNavHost(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            navController = navController,
            bottomBarVisibilityViewModel = bottomBarVisibilityViewModel
        )
    }
}