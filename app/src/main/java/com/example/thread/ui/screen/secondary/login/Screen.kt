package com.example.thread.ui.screen.secondary.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thread.data.repository.user.UserPreferences
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.input.TextField
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextCallOut
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.login.LoginDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    threadNavController: ThreadNavController,
) {
    val viewModel = LoginViewModelProvider.getInstance()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextCallOut(
            text = "Login with your Meme account",
            bold = true
        )
        Spacer(height = 16.dp)
        TextField(
            value = viewModel.username,
            onValueChange = { viewModel.updateUsername(it) },
            placeHolder = "Username"
        )
        Spacer(height = 8.dp)
        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.updatePassword(it) },
            placeHolder = "Password",
            password = true
        )
        Spacer(height = 8.dp)
        Button(
            onClick = {
                viewModel.loginSubmit {
                    UserPreferences(context).setUser(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            paddingValues = PaddingValues(16.dp),
            rounded = false,
            shape = RoundedCornerShape(12.dp),
        ) {
            TextBody(
                text = "Login",
                color = Color.White,
                bold = true
            )
        }
        Spacer(height = 16.dp)
        TextBody(
            text = "Or create an account",
            color = Color.Gray,
            modifier = Modifier.clickable {
                threadNavController.navigate(LoginDestination.SIGN_UP.route)
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    // LoginScreen()
}
