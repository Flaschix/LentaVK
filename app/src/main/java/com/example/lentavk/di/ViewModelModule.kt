package com.example.lentavk.di

import androidx.lifecycle.ViewModel
import com.example.lentavk.presentation.lenta.NewsScreenViewModel
import com.example.lentavk.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModuleKey(NewsScreenViewModel::class)
    @Binds
    fun bindNewsScreenViewModel(viewModule: NewsScreenViewModel): ViewModel

    @IntoMap
    @ViewModuleKey(MainViewModel::class)
    @Binds
    fun bindMainViewModule(viewModule: MainViewModel): ViewModel

}