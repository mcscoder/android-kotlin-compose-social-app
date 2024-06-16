package com.example.thread.ui.navigation

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.thread.ui.component.layout.NewThreadBottomSheet
import com.example.thread.ui.screen.primary.newthread.NewThreadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadBottomNavigation(threadNavController: ThreadNavController) {
    val navBackStackEntry by threadNavController.navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val displayNewThread = remember {
        mutableStateOf(false)
    }

    BottomNavigation(backgroundColor = Color.White) {
        val navigationItems = ThreadNavigationItem.entries.toTypedArray()
        navigationItems.forEach { navigationItem ->
            BottomNavigationItem(
                selected = currentDestination.toString().contains(navigationItem.route),
                icon = {
                    if (currentDestination.toString().contains(navigationItem.route)) {
                        Icon(
                            imageVector = navigationItem.selectedIcon,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    } else {
                        Icon(
                            imageVector = navigationItem.unSelectedIcon,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                },
                onClick = {
                    if (navigationItem != ThreadNavigationItem.NEW_THREAD) {
                        threadNavController.navigateToBottomBarItem(navigationItem.route)
                    } else {
                        displayNewThread.value = true
                    }
                },
            )
        }
    }

    if (displayNewThread.value) {
        val viewModel = remember {
            NewThreadViewModel()
        }

        NewThreadBottomSheet(
            viewModel = viewModel,
            threadNavController = threadNavController,
            display = displayNewThread,
            title = "New Thread",
            onPostClick = { dismiss ->
                Log.d("NewThreadBottomSheet", "NewThreadBottomSheet")
                viewModel.postThread {
                    dismiss()
                }
            }
        )
    }
}
