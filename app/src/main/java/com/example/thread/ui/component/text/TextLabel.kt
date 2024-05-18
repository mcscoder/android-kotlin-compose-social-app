package com.example.thread.ui.component.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextLabel(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = 11.sp,
    fontStyle: FontStyle? = FontStyle.Normal,
    fontWeight: FontWeight? = FontWeight.W400,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = TextDecoration.None,
    lineHeight: TextUnit = 14.sp,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            lineHeight = lineHeight,
        )
    )
    // Text(
    //     text = text,
    //     modifier = modifier,
    //     color = color,
    //     fontSize = fontSize,
    //     fontStyle = fontStyle,
    //     fontWeight = fontWeight,
    //     letterSpacing = letterSpacing,
    //     textDecoration = textDecoration,
    //     lineHeight = lineHeight,
    // )
}
