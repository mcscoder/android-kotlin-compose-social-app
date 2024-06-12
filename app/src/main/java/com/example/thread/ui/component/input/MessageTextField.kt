package com.example.thread.ui.component.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thread.data.model.common.Message
import com.example.thread.data.model.common.MessageType
import com.example.thread.ui.component.text.TextLabel

@Composable
fun MessageTextField(
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
    padding: PaddingValues = PaddingValues(16.dp),
    backgroundColor: Color = Color(245, 245, 245),
    borderColor: Color = Color.LightGray,
    shape: Shape = RoundedCornerShape(12.dp),
    startIcon: @Composable () -> Unit = {},
    endIcon: @Composable () -> Unit = {},
    singleLine: Boolean = true,
    password: Boolean = false,
    message: Message? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,

    onEnter: () -> Unit = {},
) {
    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeHolder = placeHolder,
            textStyle = textStyle,
            padding = padding,
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            shape = shape,
            startIcon = startIcon,
            endIcon = endIcon,
            singleLine = singleLine,
            password = password,
            keyboardOptions = keyboardOptions,
            onEnter = onEnter
        )
        if (message != null) {
            TextLabel(
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                text = message.text,
                color = when (message.type) {
                    MessageType.OK -> Color.Green
                    MessageType.ERROR -> Color.Red
                    MessageType.WARNING -> Color.Yellow
                }
            )
        }
    }
}
