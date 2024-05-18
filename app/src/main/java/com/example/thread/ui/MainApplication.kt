package com.example.thread.ui

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.thread.data.repository.user.UserPreferences
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.navigation.ThreadBottomNavigation
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.ThreadNavigationItem
import com.example.thread.ui.navigation.activity.activityNavGraph
import com.example.thread.ui.navigation.home.homeNavGraph
import com.example.thread.ui.navigation.myprofile.myProfileNavGraph
import com.example.thread.ui.navigation.newthread.newThreadNavGraph
import com.example.thread.ui.navigation.profile.profileNavGraph
import com.example.thread.ui.navigation.search.searchNavGraph
import com.example.thread.ui.navigation.thread.threadNavGraph
import com.example.thread.ui.screen.GlobalViewModelProvider
import com.example.thread.ui.screen.secondary.loading.LoadingScreen
import com.example.thread.ui.screen.secondary.login.LoginScreen
import com.example.thread.ui.theme.ThreadTheme

@Composable
fun MainApplication() {
    ThreadTheme(darkTheme = false) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            val threadNavController = ThreadNavController(rememberNavController())
            ThreadScaffold(
                bottomBar = {
                    ThreadBottomNavigation(threadNavController = threadNavController)
                }
            ) { paddingValues ->
                NavHost(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    navController = threadNavController.navController,
                    startDestination = ThreadNavigationItem.HOME.route,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    homeNavGraph(threadNavController)
                    searchNavGraph(threadNavController)
                    activityNavGraph(threadNavController)
                    myProfileNavGraph(threadNavController)
                    newThreadNavGraph(threadNavController)
                    profileNavGraph(threadNavController)
                    threadNavGraph(threadNavController)
                }
            }
        }
    }
}

@Composable
fun ThreadApp() {
    val userPreferences = UserPreferences(LocalContext.current)
    val userId = userPreferences.userId.collectAsState(initial = 0).value

    var isReady by remember {
        mutableStateOf(false)
    }

    if (userId == 0) {
        LoadingScreen()
    } else {
        if (userId == null) {
            isReady = false
            LoginScreen(onLoginSuccess = { userPreferences.setUser(it) })
        } else {
            isReady = true
        }
    }

    if (isReady && userId != null) {
        val globalViewModel = GlobalViewModelProvider.init(userId)
        val user = globalViewModel.user.collectAsState().value
        if (user.id == 0) {
        } else {
            MainApplication()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ThreadAppPreview() {
    ThreadApp()
}
