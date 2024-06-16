package com.example.thread.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.thread.data.repository.user.UserPreferences
import com.example.thread.ui.component.layout.NewThreadBottomSheet
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.navigation.ThreadBottomNavigation
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.ThreadNavigationItem
import com.example.thread.ui.navigation.activity.activityNavGraph
import com.example.thread.ui.navigation.followerlist.followerListNavGraph
import com.example.thread.ui.navigation.home.homeNavGraph
import com.example.thread.ui.navigation.login.LoginDestination
import com.example.thread.ui.navigation.login.loginNavGraph
import com.example.thread.ui.navigation.myprofile.myProfileNavGraph
import com.example.thread.ui.navigation.newthread.newThreadNavGraph
import com.example.thread.ui.navigation.profile.profileNavGraph
import com.example.thread.ui.navigation.search.searchNavGraph
import com.example.thread.ui.navigation.thread.threadNavGraph
import com.example.thread.ui.screen.GlobalViewModelProvider
import com.example.thread.ui.screen.primary.newthread.NewThreadViewModel
import com.example.thread.ui.screen.secondary.loading.LoadingScreen
import com.example.thread.ui.theme.ThreadTheme

@Composable
fun MainApplication() {
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
            followerListNavGraph(threadNavController)
        }
    }
}

@Composable
fun ThreadApp() {
    val userPreferences = UserPreferences(LocalContext.current)
    val userId = userPreferences.userId.collectAsState(initial = 0).value
    ThreadTheme(darkTheme = false) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            if (userId == 0) {
                // Screen loading at initial value
                LoadingScreen()
            } else if (userId == null) {
                val threadNavController = ThreadNavController(rememberNavController())
                NavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues()),
                    navController = threadNavController.navController,
                    startDestination = LoginDestination.LOGIN.route,
                ) {
                    loginNavGraph(threadNavController)
                }
                // After update if userId still null
                // LoginScreen(onLoginSuccess = { userPreferences.setUser(it) })
            } else {
                // Login authentication success
                val globalViewModel = GlobalViewModelProvider.init(userId)
                val user = globalViewModel.user.collectAsState().value
                if (user.userId != 0) {
                    // Every data about user has been fetched in GlobalViewModel
                    // App now is ready to use
                    MainApplication()
                    CommonScreens()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScreens() {
    if (GlobalViewModelProvider.displayNewThread.value) {
        val viewModel = remember {
            NewThreadViewModel()
        }

        NewThreadBottomSheet(
            viewModel = viewModel,
            display = GlobalViewModelProvider.displayNewThread,
            title = "New Thread",
            onPostClick = { dismiss ->
                viewModel.postThread {
                    dismiss()
                }
            }
        )
    }

    LaunchedEffect(GlobalViewModelProvider.displayReplyThread.value) {
        if (!GlobalViewModelProvider.displayReplyThread.value) {
            GlobalViewModelProvider.threadData.value = null
        }
    }

    if (GlobalViewModelProvider.displayReplyThread.value && GlobalViewModelProvider.threadData.value != null) {
        val viewModel = remember {
            NewThreadViewModel()
        }

        val threadData = remember {
            GlobalViewModelProvider.threadData.value!!
        }

        val thread = threadData.threadsData.data.collectAsState().value[threadData.index]

        NewThreadBottomSheet(
            viewModel = viewModel,
            display = GlobalViewModelProvider.displayReplyThread,
            title = "Reply",
            mainThread = thread,
            onPostClick = { dismiss ->
                viewModel.postReply(thread.content.type, thread.content.threadId) {
                    dismiss()
                    threadData.threadsData.updateAt(threadData.index)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ThreadAppPreview() {
    ThreadApp()
}
