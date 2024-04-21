package com.pascal.recipes_kmp.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ShimmerAnimation() {
    val transition = rememberInfiniteTransition(label = "transition")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ), label = ""
    )

    val shimmerColorShades = listOf(
        Color.LightGray.copy(0.9f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.9f)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerItem(brush = brush)
}

@Composable
fun ShimmerItem(modifier: Modifier = Modifier, brush: Brush) {
    Column(modifier = modifier.padding(vertical = 32.dp, horizontal = 24.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(
                modifier = Modifier
                    .width(100.dp)
                    .height(32.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(brush = brush)
            )
        }
        Spacer(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .width(100.dp)
                .height(24.dp)
                .background(brush = brush)
        )
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Spacer(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(80.dp)
                    .height(120.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(80.dp)
                    .height(120.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(80.dp)
                    .height(120.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(80.dp)
                    .height(120.dp)
                    .background(brush = brush)
            )
        }
        Spacer(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .width(100.dp)
                .height(24.dp)
                .background(brush = brush)
        )
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Spacer(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(160.dp)
                    .height(200.dp)
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(160.dp)
                    .height(200.dp)
                    .background(brush = brush)
            )
        }
    }
}