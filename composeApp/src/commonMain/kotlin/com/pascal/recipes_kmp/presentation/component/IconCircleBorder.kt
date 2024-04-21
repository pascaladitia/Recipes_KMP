package com.pascal.recipes_kmp.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconCircleBorder(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String = "",
    size: Dp = 32.dp,
    padding: Dp = 4.dp,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier
            .border(1.dp, Color.Black, CircleShape)
            .clip(CircleShape)
            .size(size)
            .clickable { onClick() }
            .padding(padding),
        imageVector = imageVector,
        contentDescription = contentDescription
    )
}