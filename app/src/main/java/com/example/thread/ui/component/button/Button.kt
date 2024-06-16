package com.example.thread.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.text.TextBody

enum class ButtonVariant {
    FILLED,
    OUTLINED,
    TEXT
}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)?,
    buttonVariant: ButtonVariant = ButtonVariant.FILLED,
    paddingValues: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    shape: Shape = RoundedCornerShape(8.dp),
    rounded: Boolean = true,
    disable: Boolean = false,
    content: @Composable () -> Unit,
) {
    var modifier = modifier
    when (buttonVariant) {
        ButtonVariant.FILLED -> {
            modifier = modifier.background(
                if (disable) Color.Gray else Color.Black,
                if (rounded) CircleShape else shape
            )
        }

        ButtonVariant.OUTLINED -> {
            modifier = modifier
                .background(Color.Transparent)
                .border(
                    1.dp, Color.LightGray, if (rounded) CircleShape else RoundedCornerShape(8.dp)
                )
        }

        ButtonVariant.TEXT -> {
            modifier = modifier
                .background(Color.Transparent)
        }
    }

    if (onClick != null) {
        modifier = modifier.clickable(!disable) {
            onClick()
        }
    }

    Row(
        modifier = modifier.padding(paddingValues),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun ThreadButtonPreview() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier.width(150.dp),
        buttonVariant = ButtonVariant.OUTLINED
    ) {
        TextBody(text = "Post", color = Color.Black, bold = true)
    }
}
