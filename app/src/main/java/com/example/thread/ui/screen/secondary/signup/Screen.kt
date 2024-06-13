package com.example.thread.ui.screen.secondary.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.thread.data.model.common.MessageType
import com.example.thread.data.repository.user.UserPreferences
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.input.MessageTextField
import com.example.thread.ui.component.input.TextField
import com.example.thread.ui.component.layout.SignUpLayout
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.login.LoginDestination
import com.example.thread.ui.screen.ViewModelProviderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(threadNavController: ThreadNavController) {
    val viewModel = remember {
        SignUpViewModelProvider.getInstance()
    }

    SignUpLayout(threadNavController = threadNavController, title = "Use the Email to Sign Up") {
        TextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.email.setText(it) },
            placeHolder = "Email Address"
        )
        Spacer(height = 16.dp)
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    threadNavController.navigate(LoginDestination.CODE_CONFIRMATION.route)
                    viewModel.getConfirmationCode()
                },
                rounded = false,
                paddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp)
            ) {
                TextBody(text = "Next", color = Color.White)
            }
        }
    }
}

@Composable
fun CodeConfirmationScreen(threadNavController: ThreadNavController) {
    val viewModel = remember {
        SignUpViewModelProvider.getInstance()
    }

    SignUpLayout(threadNavController = threadNavController, title = "Enter Confirmation Code") {
        TextBody(
            text = "Enter the confirmation code we sent to ${viewModel.email.value.text}.",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(height = 16.dp)
        MessageTextField(
            value = viewModel.code.value,
            onValueChange = { viewModel.code.setText(it) },
            placeHolder = "Confirmation Code",
            message = viewModel.message.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        )
        Spacer(height = 16.dp)
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.checkConfirmationCode() {
                        if (it) {
                            // Successful
                            CoroutineScope(Dispatchers.Main).launch {
                                threadNavController.navigate(LoginDestination.NAME_PASSWORD.route)
                            }
                        } else {
                            // Failed
                            viewModel.message.setMessage(
                                "That code is not valid",
                                MessageType.ERROR
                            )
                        }
                    }
                },
                rounded = false,
                paddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp)
            ) {
                TextBody(text = "Next", color = Color.White)
            }
        }
        Spacer(height = 16.dp)
        TextBody(
            text = "Resend Code.",
            color = Color.Blue,
            bold = true,
            modifier = Modifier.clickable {
                viewModel.getConfirmationCode()
                viewModel.message.setMessage("Confirmation code has been sent", MessageType.OK)
            }
        )
    }
}

@Composable
fun NameAndPasswordScreen(threadNavController: ThreadNavController) {
    val viewModel = remember {
        SignUpViewModelProvider.getInstance()
    }

    SignUpLayout(threadNavController = threadNavController, "Your name and password") {
        MessageTextField(
            value = viewModel.firstName.value,
            onValueChange = { viewModel.firstName.setText(it) },
            placeHolder = "First name"
        )
        Spacer(height = 8.dp)
        MessageTextField(
            value = viewModel.lastName.value,
            onValueChange = { viewModel.lastName.setText(it) },
            placeHolder = "Last name"
        )
        Spacer(height = 8.dp)
        MessageTextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.password.setText(it) },
            placeHolder = "Password",
            password = true
        )
        Spacer(height = 16.dp)
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.setDefaultUsername()
                    threadNavController.navigate(LoginDestination.PICK_USERNAME.route)
                },
                rounded = false,
                paddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp)
            ) {
                TextBody(text = "Next", color = Color.White)
            }
        }
    }
}

@Composable
fun PickUsername(threadNavController: ThreadNavController) {
    val viewModel = remember {
        SignUpViewModelProvider.getInstance()
    }
    val context = LocalContext.current

    SignUpLayout(threadNavController = threadNavController, title = "Pick Username") {
        MessageTextField(
            value = viewModel.username.value,
            onValueChange = { viewModel.username.setText(it) },
            placeHolder = "Username"
        )
        Spacer(height = 16.dp)
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.createAccountSubmit() { userId ->
                        UserPreferences(context).setUser(userId)
                        SignUpViewModelProvider.clear()
                    }
                },
                rounded = false,
                paddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp)
            ) {
                TextBody(text = "Sign In to Thread", color = Color.White)
            }
        }
    }
}
