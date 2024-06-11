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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.input.TextField
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextCallOut
import kotlinx.coroutines.CoroutineScope

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: CoroutineScope.(userId: Int) -> Unit = {},
) {
    val viewModel = LoginViewModelProvider.getInstance()
    var login by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextCallOut(
            text = if (login) "Login with your Meme account" else "Create a new account",
            bold = true
        )
        Spacer(height = 16.dp)
        TextField(
            value = viewModel.username,
            onValueChange = { viewModel.updateUsername(it) },
            placeHolder = "Username"
        )
        Spacer(height = 8.dp)
        if (!login) {
            TextField(
                value = viewModel.firstName,
                onValueChange = { viewModel.updateFirstName(it) },
                placeHolder = "First name"
            )
            Spacer(height = 8.dp)
            TextField(
                value = viewModel.lastName,
                onValueChange = { viewModel.updateLastName(it) },
                placeHolder = "Last name"
            )
            Spacer(height = 8.dp)
        }
        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.updatePassword(it) },
            placeHolder = "Password",
            password = true
        )
        Spacer(height = 8.dp)
        Button(
            onClick = {
                if (login) viewModel.loginSubmit(onLoginSuccess) else viewModel.createAccountSubmit(
                    onLoginSuccess
                )
            },
            modifier = Modifier.fillMaxWidth(),
            paddingValues = PaddingValues(16.dp),
            rounded = false,
            shape = RoundedCornerShape(12.dp),
            // disable = true
        ) {
            TextBody(text = if (login) "Login" else "Create new account", color = Color.White, bold = true)
        }
        Spacer(height = 16.dp)
        TextBody(
            text = if (login) "Or create an account" else "Back to login",
            color = Color.Gray,
            modifier = Modifier.clickable { login = !login }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}
