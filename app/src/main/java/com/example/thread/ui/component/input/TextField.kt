package com.example.thread.ui.component.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextField(
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
    padding: Dp = 16.dp,
    backgroundColor: Color = Color(245, 245, 245),
    borderColor: Color = Color.LightGray,
    shape: Shape = RoundedCornerShape(12.dp),
    singleLine: Boolean = true,
) {
    Row(
        modifier = modifier
            .background(backgroundColor)
            .border(1.dp, borderColor, shape)
    ) {
        BorderlessTextField(
            modifier = Modifier
                .weight(1f)
                .padding(padding),
            value = value,
            onValueChange = onValueChange,
            placeHolder = placeHolder,
            textStyle = textStyle,
            singleLine = singleLine
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TextFieldPreview() {
    var threadText = remember { TextFieldValue() }
    TextField(
        value = threadText,
        onValueChange = { threadText = it },
        placeHolder = "What's new?"
    )
}
