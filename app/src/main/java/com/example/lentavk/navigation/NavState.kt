package com.example.lentavk.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.lentavk.domain.entity.Post

class NavState(
    val navHostController: NavHostController
){
    fun navigateTo(route: String){
        navHostController.navigate(route){

            launchSingleTop = true

            popUpTo(navHostController.graph.findStartDestination().id){
                saveState = true
            }

            restoreState = true
        }
    }

    fun navigateToComments(post: Post){
        navHostController.navigate(Screen.CommentsScreen.getRouteWithArgs(post))
    }
}

@Composable
fun myRememberNavigationState(navHostController: NavHostController = rememberNavController()): NavState{
    return remember {
        NavState(navHostController)
    }
}