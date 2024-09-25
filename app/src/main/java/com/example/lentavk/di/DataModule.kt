package com.example.lentavk.di

import android.content.Context
import com.example.lentavk.data.api.ApiFactory
import com.example.lentavk.data.api.ApiService
import com.example.lentavk.data.repository.PostRepositoryImp
import com.example.lentavk.domain.repository.PostRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindRepository(impl: PostRepositoryImp): PostRepository

    companion object{
        @AppScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @AppScope
        @Provides
        fun provideVKStorage(context: Context): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }

}