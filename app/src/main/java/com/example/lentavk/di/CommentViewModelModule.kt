package com.example.lentavk.di

import androidx.lifecycle.ViewModel
import com.example.lentavk.presentation.comments.CommentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CommentViewModelModule {

    @IntoMap
    @ViewModuleKey(CommentViewModel::class)
    @Binds
    fun binCommentsViewModule(viewModule: CommentViewModel): ViewModel
}