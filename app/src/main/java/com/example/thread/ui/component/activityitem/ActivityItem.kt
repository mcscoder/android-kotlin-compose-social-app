package com.example.thread.ui.component.activityitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.button.Button
import com.example.thread.ui.component.button.ButtonVariant
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.common.ThreadHorizontalDivider
import com.example.thread.ui.component.text.TextBody
import com.example.thread.ui.component.text.TextDateTime
import com.example.thread.ui.component.user.UserAvatar

@Composable
fun ActivityItem(
    modifier: Modifier = Modifier,
    avatarURL: String,
    title: String,
    description: @Composable () -> Unit = {},
    action: @Composable() (() -> Unit?)? = null,
    onClick: () -> Unit = {},
    timeStamp: Long,
    showTimeStamp: Boolean = true,
) {
    Box(modifier = modifier
        .padding(top = 4.dp)
        .clickable { onClick() }) {
        Row(modifier = Modifier.padding(top = 12.dp, start = 16.dp)) {
            UserAvatar(avatarURL = avatarURL)
            Row {
                Spacer(width = 16.dp)
                Column {
                    Row {
                        Column(modifier = Modifier.weight(1f)) {
                            Row {
                                TextBody(text = title, bold = true)
                                Spacer(width = 4.dp)
                                if (showTimeStamp) {
                                    TextDateTime(timeStamp)
                                }
                            }
                            Spacer(height = 2.dp)
                            description()
                        }

                        if (action != null) {
                            Spacer(width = 16.dp)
                            action()
                        }
                        Spacer(width = 16.dp)
                    }
                    Spacer(height = 16.dp)
                    ThreadHorizontalDivider()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ActivityItemPreview() {
    Column {
        ActivityItem(
            avatarURL = "asdfs",
            title = "iamnalimov",
            timeStamp = 0,
            description = { TextBody(text = "Followed you", color = Color.Gray) },
            action = {
                Button(
                    onClick = { /*TODO*/ },
                    buttonVariant = ButtonVariant.OUTLINED,
                    rounded = false
                ) {
                    TextBody(text = "Follow", bold = true)
                }
            })
        ActivityItem(avatarURL = "asdfddfs", title = "ok", timeStamp = 0)
    }
}
