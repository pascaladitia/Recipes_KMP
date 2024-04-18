package presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import compose.icons.FeatherIcons
import compose.icons.feathericons.Camera
import compose.icons.feathericons.File
import compose.icons.feathericons.X

@Composable
fun CameraGalleryDialog(
    showDialogCapture: Boolean = false,
    onSelect: (ByteArray?) -> Unit,
    onDismiss: () -> Unit
) {
    var showCamera by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val galleryLauncher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                onSelect(it)
            }
        }
    )
    val cameraLauncher = rememberPeekabooCameraState(onCapture = {
        showCamera = false
        onSelect(it)
    })

    if (showCamera) {
        Box {
            PeekabooCamera(
                state = cameraLauncher,
                modifier = Modifier.fillMaxSize(),
                permissionDeniedContent = {

                },
            )
            CameraOverlay(
                isCapturing = cameraLauncher.isCapturing,
                onBack = {
                    showCamera = false
                    onDismiss()
                },
                onCapture = { cameraLauncher.capture() },
                onConvert = { cameraLauncher.toggleCamera() },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }

    if (showDialogCapture) {
        AlertDialog(
            onDismissRequest = {  },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = "Select Foto Source"
                    )
                    Icon(
                        imageVector = FeatherIcons.X,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .clip(CircleShape)
                            .clickable { onDismiss() }
                    )
                }
            },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                onDismiss()
                                showCamera = true
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            CameraGalleryButton(
                                icon = FeatherIcons.Camera,
                                text = "Camera"
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                galleryLauncher.launch()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            CameraGalleryButton(
                                icon = FeatherIcons.File,
                                text = "Gallery"
                            )
                        }
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.background,
            confirmButton = {},
            dismissButton = {}
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
            onClick = { onBack() },
            modifier = Modifier.align(Alignment.TopStart)
                .padding(top = 48.dp, start = 16.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Close,
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
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Back Chanched",
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier.align(Alignment.BottomEnd)
                .clickable { onConvert() }
                .padding(bottom = 16.dp, end = 16.dp),
        )
        Icon(
            imageVector = Icons.Default.AddCircle,
            contentDescription = "Button Capture",
            tint = Color.White.copy(alpha = 0.7f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable { onCapture() }
                .padding(bottom = 16.dp),
        )
    }
}

@Composable
private fun CameraGalleryButton(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text)
    }
}