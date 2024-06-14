package com.example.thread.ui.component.input

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thread.ui.component.text.TextBody

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeHolder: String? = null,
    label: String? = null,
    textStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 15.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.W400,
        letterSpacing = (-0.25).sp,
        textDecoration = TextDecoration.None,
        lineHeight = 21.sp,
    ),
    padding: PaddingValues = PaddingValues(16.dp),
    backgroundColor: Color = Color(245, 245, 245),
    borderColor: Color = Color.LightGray,
    shape: Shape = RoundedCornerShape(12.dp),
    startIcon: @Composable () -> Unit = {},
    endIcon: @Composable () -> Unit = {},
    singleLine: Boolean = true,
    minLines: Int = 1,
    password: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onEnter: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .border(1.dp, borderColor, shape),
    ) {
        if (!label.isNullOrEmpty()) {
            TextBody(
                text = label,
                bold = true,
                modifier = Modifier.padding(
                    start = padding.calculateStartPadding(LayoutDirection.Ltr),
                    top = 16.dp
                )
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            startIcon()
            BorderlessTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(padding),
                value = value,
                onValueChange = onValueChange,
                placeHolder = placeHolder,
                textStyle = textStyle,
                singleLine = singleLine,
                minLines = minLines,
                password = password,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    onEnter()
                }),
            )
            endIcon()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TextFieldPreview() {
    var threadText = remember { TextFieldValue() }
    TextField(
        value = threadText,
        onValueChange = { threadText = it },
        placeHolder = "What's new?",
        label = "Bio"
    )
}
