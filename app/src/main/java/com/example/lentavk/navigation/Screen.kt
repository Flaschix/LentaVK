package com.example.lentavk.navigation

import com.example.lentavk.domain.entity.Post
import com.example.lentavk.utils.encode
import com.google.gson.Gson

sealed class Screen(
    val route: String
){
    object NewsScreen : Screen (ROUTE_NEWS_POST)
    object CommentsScreen : Screen (ROUTE_COMMENTS_POST){

        private const val ROUTE_FOR_ARGS = "CommentsFeed"

        fun getRouteWithArgs(post: Post): String {
            val feedPostJson = Gson().toJson(post)
            return "$ROUTE_FOR_ARGS/${ feedPostJson.encode() }"
        }
    }

    object Home : Screen (ROUTE_HOME_POST)

    companion object{
        const val KEY_POST = "key_post"

        const val ROUTE_NEWS_POST = "NewsScreen"
        const val ROUTE_COMMENTS_POST = "CommentsScreen/{$KEY_POST}"
        const val ROUTE_HOME_POST = "HomeFeed"
    }
}