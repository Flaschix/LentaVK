package com.example.lentavk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lentavk.domain.entity.Post

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    commentsScreenContent: @Composable (post: Post) -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
) {

    NavHost(navController = navHostController, startDestination = Screen.Home.route) {
        homeScreenNavGraph(
            commentsScreenContent = commentsScreenContent,
            newsFeedScreenContent = newsFeedScreenContent,
        )
    }
}