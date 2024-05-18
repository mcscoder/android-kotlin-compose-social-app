package com.example.thread.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

// Use primary for screens those show the bottom navigation bar
// Use secondary for screens those hide the bottom navigation bar
// Should be used combine with the getNavRoute() function below
// For more information pls consider how it is implemented by following where it was used
enum class NavigationType(
    val route: String
) {
    PRIMARY("primary"),
    SECONDARY("secondary")
}

fun getNavRoute(navigationType: NavigationType, vararg routes: String): String {
    return "${navigationType.route}/${routes.joinToString(separator = "/")}"
}

enum class ThreadNavigationItem(
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val route: String,
) {
    HOME(Icons.Filled.Home, Icons.Outlined.Home, "home"),
    SEARCH(Icons.Filled.Search, Icons.Outlined.Search, "search"),
    NEW_THREAD(Icons.Filled.Edit, Icons.Outlined.Edit, "newThread"),
    ACTIVITY(Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "activity"),
    MY_PROFILE(Icons.Filled.Person, Icons.Outlined.Person, "myProfile")
}
