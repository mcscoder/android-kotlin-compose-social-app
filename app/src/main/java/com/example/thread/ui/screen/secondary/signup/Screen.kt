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
import com.example.thread.data.repository.user.UserPreferences
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.common.rememberAlertDialog
import com.example.thread.ui.component.input.MessageTextField
import com.example.thread.ui.component.input.TextField
import com.example.thread.ui.component.layout.SignUpLayout
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.navigation.ThreadNavController
import com.example.thread.ui.navigation.login.LoginDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EnterEmailScreen(
    threadNavController: ThreadNavController,
    title: String,
    onResponse: CoroutineScope.(isExists: Boolean, existAlert: () -> Unit, notExistAlert: () -> Unit) -> Unit,
) {
    val viewModel = remember {
        SignUpViewModelProvider.getInstance()
    }

    val invalidEmailAlert = rememberAlertDialog(
        title = "Invalid email.",
        text = "The email you entered is invalid. Please try again."
    )

    val emailExistsAlert = rememberAlertDialog(
        title = "Email Exists",
        text = "The email address you entered already exists."
    )

    val emailNotExistsAlert = rememberAlertDialog(
        title = "Email does not exists",
        text = "The email address you entered does not exists. Please try again."
    )

    SignUpLayout(threadNavController = threadNavController, title = title) {
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
                    viewModel.isEmailExist(
                        onInvalidEmail = {
                            invalidEmailAlert()
                        }
                    ) { isExists ->
                        onResponse(isExists, emailExistsAlert, emailNotExistsAlert)
                        // if (isExists) {
                        //     emailExistsAlert()
                        // } else {
                        //     threadNavController.navigate(LoginDestination.SIGN_UP_CODE_CONFIRMATION.route)
                        //     viewModel.getConfirmationCode()
                        // }
                    }
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
fun CodeConfirmationScreen(threadNavController: ThreadNavController, onValidCode: () -> Unit) {
    val viewModel = remember {
        SignUpViewModelProvider.getInstance()
    }

    val resendCodeAlert = rememberAlertDialog(
        title = "Code has been sent.",
        text = "Your confirmation code has been sent, please check your email inbox."
    )

    val invalidCodeAlert = rememberAlertDialog(
        title = "Invalid code.",
        text = "The code you entered is incorrect.\nPlease try again or get a new code by click \"Resend Code\" button below"
    )

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
                                onValidCode()
                            }
                        } else {
                            // Failed
                            invalidCodeAlert()
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
                viewModel.getConfirmationCode() {
                    resendCodeAlert()
                }
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

@Composable
fun NewPasswordScreen(threadNavController: ThreadNavController) {
    val viewModel = remember {
        SignUpViewModelProvider.getInstance()
    }

    SignUpLayout(threadNavController = threadNavController, title = "New password") {
        MessageTextField(
            value = viewModel.newPassword.value,
            onValueChange = { viewModel.newPassword.setText(it) },
            placeHolder = "New password",
            password = true
        )
        Spacer(height = 16.dp)
        Row {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.updateNewPassword {
                        threadNavController.navigate(LoginDestination.LOGIN.route)
                    }
                },
                rounded = false,
                paddingValues = PaddingValues(horizontal = 20.dp, vertical = 14.dp)
            ) {
                TextBody(text = "Change to new password", color = Color.White)
            }
        }
    }
}
