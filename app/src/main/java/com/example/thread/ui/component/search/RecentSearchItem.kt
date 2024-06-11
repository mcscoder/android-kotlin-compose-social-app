package com.example.thread.ui.component.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thread.ui.component.common.Spacer
import com.example.thread.ui.component.common.ThreadHorizontalDivider
import com.example.thread.ui.component.icon.IconNormal
import com.example.thread.ui.component.text.TextBody

@Composable
fun RecentSearchItem(modifier: Modifier = Modifier, text: String, onClick: () -> Unit = {}) {
    Box(modifier = modifier
        .padding(top = 4.dp)
        .clickable { onClick() }) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 12.dp),
        ) {
            IconNormal(imageVector = Icons.Filled.Search, tint = Color.Gray)
            Spacer(width = 16.dp)
            Column(modifier = Modifier.weight(1f)) {
                TextBody(text = text, bold = true)
                Spacer(height = 16.dp)
                ThreadHorizontalDivider()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RecentSearchItem() {
    Column {
        RecentSearchItem(text = "son dep trai")
        RecentSearchItem(text = "son dep trai")
        RecentSearchItem(text = "son dep trai")
        RecentSearchItem(text = "son dep trai")
        RecentSearchItem(text = "son dep trai")
    }
}
