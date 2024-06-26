package com.example.thread.ui.component.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.thread.ui.component.button.IconClickable
import com.example.thread.ui.component.common.ThreadHorizontalDivider
import com.example.thread.ui.component.text.TextCallOut
import com.example.thread.ui.navigation.ThreadNavController

@Composable
fun ThreadTopBar(
    modifier: Modifier = Modifier,
    threadNavController: ThreadNavController? = null,
    title: String = "",
    actions: @Composable RowScope.() -> Unit = {},
    onNavigateUp: () -> Unit = {},
    showDivider: Boolean = true,
    showBackButton: Boolean = true,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.width(48.dp)) {
                if (showBackButton) {
                    IconClickable(
                        imageVector = Icons.Rounded.ArrowBack,
                        onClick = {
                            threadNavController?.navigateUp()
                            onNavigateUp()
                        }
                    )
                }
            }
            Row(modifier = Modifier.weight(1f)) {
                TextCallOut(
                    text = title,
                    bold = true
                )
            }
            Row(modifier = Modifier) {
                actions()
            }
        }
        if (showDivider) {
            ThreadHorizontalDivider()
        }
    }
    // TopAppBar(
    //     backgroundColor = Color.White,
    //     navigationIcon = {
    //         IconClickable(
    //             imageVector = Icons.Rounded.ArrowBack,
    //             onClick = {
    //                 threadNavController.navigateUp()
    //             }
    //         )
    //     },
    //     title = {
    //         TextCallOut(
    //             text = title,
    //             bold = true
    //         )
    //     },
    //     actions = actions,
    //     modifier = Modifier.shadow(0.dp),
    // )
}

@Preview
@Composable
private fun ThreadAppTopBarPreview() {
    ThreadTopBar(
        threadNavController = ThreadNavController(rememberNavController()),
        title = "New Thread",
    )
}
