package com.pascal.recipes_kmp.presentation.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.ResizeOptions
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
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

    val resizeOptions = ResizeOptions(
        width = 1000, // Custom width
        height = 1000, // Custom height
        resizeThresholdBytes = 1 * 512 * 512L, // Custom threshold for 2MB,
        compressionQuality = 1.0 // Adjust compression quality (0.0 to 1.0)
    )
    val galleryLauncher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        resizeOptions = resizeOptions,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                onSelect(it)
            }
        }
    )

    if (showCamera) {
        Box {
            PeekabooCameraView(
                modifier = Modifier.fillMaxSize(),
                onBack = {
                    showCamera = false
                    onDismiss()
                },
                onCapture = { byteArray ->
                    byteArray?.let {
                        showCamera = false
                        onSelect(it)
                    }
                    showCamera = false
                },
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
private fun CameraGalleryButton(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text)
    }
}