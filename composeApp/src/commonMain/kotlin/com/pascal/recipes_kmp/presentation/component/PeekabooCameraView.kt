package com.pascal.recipes_kmp.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState

@Composable
fun PeekabooCameraView(
    modifier: Modifier = Modifier,
    onCapture: (ByteArray?) -> Unit,
    onBack: () -> Unit,
) {
    val state = rememberPeekabooCameraState(onCapture = onCapture)
    Box(modifier = modifier) {
        PeekabooCamera(
            state = state,
            modifier = Modifier.fillMaxSize(),
            permissionDeniedContent = {
                PermissionDenied(
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
        CameraOverlay(
            isCapturing = state.isCapturing,
            onBack = onBack,
            onCapture = { state.capture() },
            onConvert = { state.toggleCamera() },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun PermissionDenied(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = IconWarning,
            contentDescription = "Warning Icon",
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Please grant the camera permission!",
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun CameraOverlay(
    isCapturing: Boolean,
    onCapture: () -> Unit,
    onConvert: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        IconButton(
            onClick = onBack,
            modifier =
            Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp),
        ) {
            Icon(
                imageVector = IconClose,
                contentDescription = "Back Button",
                tint = Color.White,
            )
        }
        if (isCapturing) {
            CircularProgressIndicator(
                modifier =
                Modifier
                    .size(80.dp)
                    .align(Alignment.Center),
                color = Color.White.copy(alpha = 0.7f),
                strokeWidth = 8.dp,
            )
        }
        CircularButton(
            imageVector = IconCached,
            modifier =
            Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            onClick = onConvert,
        )
        InstagramCameraButton(
            modifier =
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            onClick = onCapture,
        )
    }
}

internal val IconClose =
    materialIcon(name = "Filled.Close") {
        materialPath {
            moveTo(19.0f, 6.41f)
            lineTo(17.59f, 5.0f)
            lineTo(12.0f, 10.59f)
            lineTo(6.41f, 5.0f)
            lineTo(5.0f, 6.41f)
            lineTo(10.59f, 12.0f)
            lineTo(5.0f, 17.59f)
            lineTo(6.41f, 19.0f)
            lineTo(12.0f, 13.41f)
            lineTo(17.59f, 19.0f)
            lineTo(19.0f, 17.59f)
            lineTo(13.41f, 12.0f)
            close()
        }
    }

@Composable
internal fun InstagramCameraButton(
    modifier: Modifier = Modifier,
    size: Dp = 70.dp,
    borderSize: Dp = 5.dp,
    onClick: () -> Unit,
) {
    // Outer size including the border
    val outerSize = size + borderSize * 2
    // Inner size excluding the border
    val innerSize = size - borderSize

    Box(
        modifier =
        modifier
            .size(outerSize)
            .clip(CircleShape)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        // Surface for the border effect
        Surface(
            modifier = Modifier.size(outerSize),
            shape = CircleShape,
            color = Color.Transparent,
            border = BorderStroke(borderSize, Color.White),
        ) {}

        // Inner clickable circle
        Box(
            modifier =
            Modifier
                .size(innerSize)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {}
    }
}

@Composable
fun CircularButton(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSurface)
            .run {
                if (enabled) {
                    clickable { onClick() }
                } else {
                    this
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
fun CircularButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    CircularButton(
        modifier = modifier,
        content = {
            Icon(imageVector, null, Modifier.size(34.dp), Color.White)
        },
        enabled = enabled,
        onClick = onClick,
    )
}

internal val IconCached =
    materialIcon(name = "Filled.Cached") {
        materialPath {
            moveTo(19.0f, 8.0f)
            lineToRelative(-4.0f, 4.0f)
            horizontalLineToRelative(3.0f)
            curveToRelative(0.0f, 3.31f, -2.69f, 6.0f, -6.0f, 6.0f)
            curveToRelative(-1.01f, 0.0f, -1.97f, -0.25f, -2.8f, -0.7f)
            lineToRelative(-1.46f, 1.46f)
            curveTo(8.97f, 19.54f, 10.43f, 20.0f, 12.0f, 20.0f)
            curveToRelative(4.42f, 0.0f, 8.0f, -3.58f, 8.0f, -8.0f)
            horizontalLineToRelative(3.0f)
            lineToRelative(-4.0f, -4.0f)
            close()
            moveTo(6.0f, 12.0f)
            curveToRelative(0.0f, -3.31f, 2.69f, -6.0f, 6.0f, -6.0f)
            curveToRelative(1.01f, 0.0f, 1.97f, 0.25f, 2.8f, 0.7f)
            lineToRelative(1.46f, -1.46f)
            curveTo(15.03f, 4.46f, 13.57f, 4.0f, 12.0f, 4.0f)
            curveToRelative(-4.42f, 0.0f, -8.0f, 3.58f, -8.0f, 8.0f)
            horizontalLineTo(1.0f)
            lineToRelative(4.0f, 4.0f)
            lineToRelative(4.0f, -4.0f)
            horizontalLineTo(6.0f)
            close()
        }
    }

internal val IconWarning =
    materialIcon(name = "Filled.Warning") {
        materialPath {
            moveTo(1.0f, 21.0f)
            horizontalLineToRelative(22.0f)
            lineTo(12.0f, 2.0f)
            lineTo(1.0f, 21.0f)
            close()
            moveTo(13.0f, 18.0f)
            horizontalLineToRelative(-2.0f)
            verticalLineToRelative(-2.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(2.0f)
            close()
            moveTo(13.0f, 14.0f)
            horizontalLineToRelative(-2.0f)
            verticalLineToRelative(-4.0f)
            horizontalLineToRelative(2.0f)
            verticalLineToRelative(4.0f)
            close()
        }
    }