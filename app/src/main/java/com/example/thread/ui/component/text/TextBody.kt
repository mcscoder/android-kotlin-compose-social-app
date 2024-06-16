package com.example.thread.ui.component.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextBody(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    fontSize: TextUnit = 15.sp,
    fontStyle: FontStyle? = FontStyle.Normal,
    fontWeight: FontWeight? = FontWeight.W400,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = TextDecoration.None,
    lineHeight: TextUnit = 21.sp,
    textAlign: TextAlign = TextAlign.Unspecified,
    bold: Boolean = false,
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = if (bold) FontWeight.W600 else fontWeight,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            lineHeight = lineHeight,
            textAlign = textAlign
        )
    )
    // Text(
    //     text = text,
    //     modifier = modifier,
    //     color = color,
    //     fontSize = fontSize,
    //     fontStyle = fontStyle,
    //     fontWeight = if (bold) FontWeight.W600 else fontWeight,
    //     letterSpacing = letterSpacing,
    //     textDecoration = textDecoration,
    //     lineHeight = lineHeight,
    // )
}
