package com.example.lentavk.presentation.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.lentavk.navigation.AppNavGraph
import com.example.lentavk.navigation.myRememberNavigationState
import com.example.lentavk.presentation.comments.CommentScreen
import com.example.lentavk.presentation.factory.ViewModelFactory
import com.example.lentavk.presentation.lenta.NewsScreen

@Composable
fun MainScreen(viewModelFactory: ViewModelFactory){

    val navigationState = myRememberNavigationState()

    Scaffold{
        it;

        AppNavGraph(
            navHostController = navigationState.navHostController,

            newsFeedScreenContent = { NewsScreen(viewModelFactory, onCommentClickListener = {
                navigationState.navigateToComments(it)
            }) },

            commentsScreenContent = { post ->
                CommentScreen(
                    onBackPressedListener = { navigationState.navHostController.popBackStack() },
                    post = post,
                )
            }
        )
    }
}