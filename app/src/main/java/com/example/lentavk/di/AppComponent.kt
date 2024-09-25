package com.example.lentavk.di

import android.content.Context
import com.example.lentavk.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun getCommentsScreenComponentFactory(): CommentScreenComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance context: Context,
        ): AppComponent
    }
}