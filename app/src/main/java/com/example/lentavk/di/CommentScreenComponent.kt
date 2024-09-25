package com.example.lentavk.di

import com.example.lentavk.domain.entity.Post
import com.example.lentavk.presentation.factory.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [CommentViewModelModule::class])
interface CommentScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory{

        fun create(@BindsInstance post: Post) : CommentScreenComponent
    }
}