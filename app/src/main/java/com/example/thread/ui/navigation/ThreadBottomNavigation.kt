package com.example.thread.ui.navigation

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun ThreadBottomNavigation(threadNavController: ThreadNavController) {
    Log.d("re-render", "[run when destination is changed] BottomBarNavigation")
    val navBackStackEntry by threadNavController.navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    // if (currentDestination.toString().startsWith(NavigationType.PRIMARY.route)) {
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
                        if (!currentDestination.toString().contains(navigationItem.route)) {
                            threadNavController.navigateToBottomBarItem(navigationItem.route)
                        }
                    },
                )
            }
        }
    // }
}
