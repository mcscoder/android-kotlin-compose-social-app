package com.example.thread.ui.navigation

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.example.thread.ui.navigation.myprofile.MyProfileDestination
import com.example.thread.ui.navigation.profile.ProfileDestination
import com.example.thread.ui.screen.GlobalViewModelProvider

@Stable
class ThreadNavController(val navController: NavHostController) {
    // var showBottomNavigation by mutableStateOf(true)

    fun navigate(path: String, optionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
        val currentRoute = navController.currentDestination?.route
        if (currentRoute != path) {
            navController.navigate(path) {
                optionsBuilder()
            }
        }
    }

    fun navigateToBottomBarItem(path: String) {
        navigate(path) {
            // if (path != ThreadNavigationItem.NEW_THREAD.route) {
            launchSingleTop = true
            restoreState = true
            // Pop up backstack to the first destination and save state. This makes going back
            // to the start destination when pressing back in any other bottom tab.
            popUpTo(ThreadNavigationItem.HOME.route) {
                saveState = true
            }
            // }
        }
    }

    fun navigateToUserProfile(userId: Int) {
        if (userId == GlobalViewModelProvider.getInstance().getUser().id) {
            navigateToBottomBarItem(MyProfileDestination.START_DESTINATION.route)
        } else {
            navigate("${ProfileDestination.PROFILE.route}/${userId}")
        }
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}
