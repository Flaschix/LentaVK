package com.example.lentavk.presentation.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.lentavk.R
import com.example.lentavk.domain.entity.Post
import com.example.lentavk.domain.entity.PostComment
import com.example.lentavk.presentation.app.ApplicationVK

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(
    post: Post,
    onBackPressedListener: () -> Unit
) {
    val component = (LocalContext.current.applicationContext as ApplicationVK)
        .component
        .getCommentsScreenComponentFactory()
        .create(post)

    val viewModel: CommentViewModel = viewModel(factory = component.getViewModelFactory())

    val screenState = viewModel.screenState.collectAsState(CommentScreenState.Init)
    val currentState = screenState.value

    if(currentState is CommentScreenState.Comments){
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = stringResource(R.string.comments_title))
                },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressedListener() }) {
                            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            it;
            LazyColumn(
                contentPadding = PaddingValues(top = 60.dp, start = 6.dp, end = 6.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(currentState.comments, key = { it.id }) { comment ->
                    CommentItem(comment = comment)
                }
            }
        }
    }


}

@Composable
private fun CommentItem(comment: PostComment){
    Column(modifier = Modifier.padding(start = 6.dp, top = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = comment.avatarUrl,
                contentDescription = null,
                modifier = Modifier.size(50.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = comment.authorName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = comment.date,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = comment.text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
    }

}