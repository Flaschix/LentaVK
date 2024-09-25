package com.example.lentavk.presentation.lenta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lentavk.domain.entity.Post
import com.example.lentavk.presentation.factory.ViewModelFactory


@Composable
fun NewsScreen(
    viewModelFactory: ViewModelFactory,
    onCommentClickListener: (post: Post) -> Unit
){
    val viewModel: NewsScreenViewModel = viewModel(factory = viewModelFactory)
    val screenState = viewModel.screenState.collectAsState(NewsScreenState.Init)

    when (val currentState = screenState.value) {
        is NewsScreenState.Success -> ShowPost(
            posts = currentState.posts,
            viewModel = viewModel,
            onCommentClickListener = onCommentClickListener,
            nextDataIsLoading = currentState.nexDataIsLoading
        )

        is NewsScreenState.Init -> {}
        is NewsScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = Color.Blue)
            }
        }
    }
}

@Composable
private fun ShowPost(
    posts: List<Post>,
    viewModel: NewsScreenViewModel,
    onCommentClickListener: (post: Post) -> Unit,
    nextDataIsLoading: Boolean
){
    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, start = 6.dp, end = 6.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ){
        items(posts, key = {it.id} ){ post ->
            PostBox(
                post = post,

                onCommentClickListener = {
                    onCommentClickListener(post)
                },
                onLikeClickListener = { newItem ->
                    viewModel.changeLikeStatus(post)
                },
            )
        }
        item {
            if(nextDataIsLoading){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(color = Color.Blue)
                }
            }else {
                SideEffect {
                    viewModel.loadNextPosts()
                }
            }
        }
    }
}