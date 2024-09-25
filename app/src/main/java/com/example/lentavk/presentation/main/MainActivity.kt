package com.example.lentavk.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lentavk.domain.entity.UserState
import com.example.lentavk.presentation.app.ApplicationVK
import com.example.lentavk.presentation.factory.ViewModelFactory
import com.example.lentavk.presentation.ui.theme.LentaVKTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ApplicationVK).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LentaVKTheme {

                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
                val userState = viewModel.authState.collectAsState(UserState.Init)

                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ){
                    viewModel.performAuthResult()
                }

                when(userState.value){
                    is UserState.Authorized -> MainScreen(viewModelFactory)
                    is UserState.NotAuthorized -> LoginScreen{
                        launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                    }

                    else -> {}
                }
            }
        }
    }
}
