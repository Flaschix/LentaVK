package com.example.lentavk.presentation.comments

import androidx.lifecycle.ViewModel
import com.example.lentavk.domain.entity.Post
import com.example.testimcompose.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentViewModel @Inject constructor(
    private val post: Post,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    val screenState = getCommentsUseCase(post)
        .map { CommentScreenState.Comments(post, it) as CommentScreenState }

}