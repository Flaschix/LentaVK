package com.example.lentavk.presentation.app

import android.app.Application
import com.example.lentavk.di.AppComponent
import com.example.lentavk.di.DaggerAppComponent

class ApplicationVK : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}