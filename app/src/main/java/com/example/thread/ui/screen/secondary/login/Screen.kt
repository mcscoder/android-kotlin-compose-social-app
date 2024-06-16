package com.example.thread.ui.screen.secondary.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thread.data.repository.user.UserPreferences
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.common.rememberAlertDialog
import com.example.thread.ui.component.input.TextField
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextCallOut
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.login.LoginDestination

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    threadNavController: ThreadNavController,
) {
    val viewModel = LoginViewModelProvider.getInstance()
    val context = LocalContext.current

    val loginFailedAlert = rememberAlertDialog(
        title = "Email or password is incorrect.",
        text = "The email or password you entered is incorrect. Please try again."
    )

    val invalidEmailAlert = rememberAlertDialog(
        title = "Invalid email.",
        text = "The email you entered is invalid. Please try again."
    )

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
            value = viewModel.email,
            onValueChange = { viewModel.updateUsername(it) },
            placeHolder = "Email"
        )
        Spacer(height = 8.dp)
        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.updatePassword(it) },
            placeHolder = "Password",
            password = true
        )
        Spacer(height = 12.dp)
        Box(modifier = Modifier.fillMaxWidth()) {
            TextBody(
                text = "Forgot password?",
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        threadNavController.navigate(LoginDestination.FORGOT_PASSWORD.route)
                    }
            )
        }
        Spacer(height = 28.dp)
        Button(
            onClick = {
                viewModel.loginSubmit(
                    onInvalidEmail = {
                        invalidEmailAlert()
                    }
                ) { userId ->
                    if (userId != null) {
                        // Authentication success
                        UserPreferences(context).setUser(userId)
                    } else {
                        // Authentication failed
                        // Display a failed dialog
                        loginFailedAlert()
                    }
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
        Spacer(height = 30.dp)
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
