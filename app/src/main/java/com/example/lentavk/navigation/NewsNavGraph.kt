package com.example.lentavk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.lentavk.domain.entity.Post
import com.google.gson.Gson

fun NavGraphBuilder.homeScreenNavGraph(
    commentsScreenContent: @Composable (post: Post) -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
){
    navigation(
        startDestination = Screen.NewsScreen.route,
        route = Screen.Home.route,
        builder = {
            composable(Screen.NewsScreen.route) {
                newsFeedScreenContent()
            }

            composable(
                route = Screen.CommentsScreen.route,
                arguments = listOf(
                    navArgument(Screen.KEY_POST){
                        type = NavType.StringType
                    }
                )
            ) {
                val feedPostJson = it.arguments?.getString(Screen.KEY_POST) ?: ""

                val feedPost = Gson().fromJson<Post>(feedPostJson, Post::class.java)
                commentsScreenContent(feedPost)
            }
        }
    )
}