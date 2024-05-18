package com.example.thread.ui.screen.secondary.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.thread.data.repository.user.UserPreferences
import com.example.thread.ui.component.navigation.ThreadTopBar
import com.example.thread.ui.component.scaffold.ThreadScaffold
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.modifier.noRippleClickable
import com.example.thread.ui.navigation.ThreadNavController

@Composable
fun SettingScreen(threadNavController: ThreadNavController) {
    val userPreferences = UserPreferences(LocalContext.current)

    ThreadScaffold(
        topBar = {
            ThreadTopBar(
                threadNavController = threadNavController,
                title = "Settings",
                showDivider = false,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextBody(
                    text = "Log out",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            userPreferences.clearUser()
                        })
            }
        }
    }
}
