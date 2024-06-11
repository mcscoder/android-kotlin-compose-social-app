package com.example.thread.ui.component.input

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.thread.ui.component.text.TextBody
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BorderlessTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeHolder: String? = null,
    textStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 15.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.25).sp,
        textDecoration = TextDecoration.None,
        lineHeight = 21.sp,
    ),
    singleLine: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions(),
    password: Boolean = false,
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        onTextLayout = {
            val cursorRect = it.getCursorRect(value.selection.start)
            coroutineScope.launch {
                Log.d("ThreadTextField", value.selection.start.toString())
                bringIntoViewRequester.bringIntoView(cursorRect)
            }
        },
        modifier = modifier.bringIntoViewRequester(bringIntoViewRequester),
        decorationBox = { innerTextField ->
            if (!placeHolder.isNullOrBlank() && value.text.isEmpty()) {
                TextBody(text = placeHolder, color = Color.Gray)
            }
            innerTextField()
        },
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Preview(showBackground = true)
@Composable
private fun ThreadTextFieldPreview() {
    var threadText = remember { TextFieldValue() }
    BorderlessTextField(
        value = threadText,
        onValueChange = { threadText = it },
        placeHolder = "What's new?"
    )
}
