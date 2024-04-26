package com.pascal.recipes_kmp.presentation.screen.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.pascal.recipes_kmp.domain.usecases.UiState
import com.pascal.recipes_kmp.presentation.component.CameraGalleryDialog
import com.pascal.recipes_kmp.presentation.component.ErrorScreen
import com.pascal.recipes_kmp.presentation.component.LoadingScreen
import com.pascal.recipes_kmp.utils.Base64.decodeFromBase64
import com.pascal.recipes_kmp.utils.Base64.encodeToBase64
import com.preat.peekaboo.image.picker.toImageBitmap
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import recipes_kmp.composeapp.generated.resources.Res
import recipes_kmp.composeapp.generated.resources.no_profile
import recipes_kmp.composeapp.generated.resources.no_thumbnail
import sqldelight.db.ProfileEntity

class ProfileScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val coroutineScope = rememberCoroutineScope()
        val viewModel: ProfileViewModel = koinInject<ProfileViewModel>()
        var isContentVisible by remember { mutableStateOf(false) }
        var editMode by remember { mutableStateOf(false) }

        val uiState by viewModel.profile.collectAsState()

        LaunchedEffect(key1 = true) {
            viewModel.loadProfile()
        }

        Surface(
            modifier = Modifier.padding(PaddingValues(vertical = 48.dp)),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimatedVisibility(
                visible = uiState !is UiState.Loading,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Crossfade(targetState = uiState, label = "") { state ->
                    when (state) {
                        is UiState.Loading -> {
                            LoadingScreen()
                        }

                        is UiState.Error -> {
                            val message = state.message
                            ErrorScreen(message = message) {}
                        }

                        is UiState.Empty -> {
                            ProfileEditContent(
                                itemProfile = null,
                                onSave = {
                                    viewModel.addProfile(it)
                                    editMode = false

                                    coroutineScope.launch {
                                        viewModel.loadProfile()
                                    }
                                },
                                onMaps = {
//                                    onMaps()
                                }
                            )
                        }

                        is UiState.Success -> {
                            val data = state.data
                            if (editMode) {
                                ProfileEditContent(
                                    itemProfile = data,
                                    onSave = {
                                        viewModel.addProfile(it)
                                        editMode = false

                                        coroutineScope.launch {
                                            viewModel.loadProfile()
                                        }
                                    },
                                    onMaps = {
//                                        onMaps()
                                    }
                                )
                            } else {
                                ProfileContent(
                                    itemProfile = data,
                                    onEdit = {
                                        editMode = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    itemProfile: ProfileEntity?,
    onEdit: () -> Unit,
) {
    var name by remember { mutableStateOf(itemProfile?.name) }
    var email by remember { mutableStateOf(itemProfile?.email) }
    var phone by remember { mutableStateOf(itemProfile?.phone) }
    var address by remember { mutableStateOf(itemProfile?.address ?: "-") }
    var imageByteArray by remember {
        mutableStateOf<ByteArray?>(
            if (itemProfile?.imagePath.isNullOrEmpty()) null else itemProfile?.imagePath?.decodeFromBase64()
        )
    }
    var imageProfileByteArray by remember {
        mutableStateOf<ByteArray?>(
            if (itemProfile?.imageProfilePath.isNullOrEmpty()) null else itemProfile?.imageProfilePath?.decodeFromBase64()
        )
    }

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (image, imageProfile, formField, cardView) = createRefs()

        if (imageByteArray != null) {
            Image(
                bitmap = imageByteArray!!.toImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.no_thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(cardView) {
                    top.linkTo(image.top, margin = 180.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
        )

        if (imageProfileByteArray != null) {
            Image(
                bitmap = imageProfileByteArray!!.toImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(6.dp, Color.White, CircleShape)
                    .clip(CircleShape)
                    .size(150.dp)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .constrainAs(imageProfile) {
                        centerHorizontallyTo(parent)
                        centerAround(cardView.top)
                    }
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.no_profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(6.dp, Color.White, CircleShape)
                    .clip(CircleShape)
                    .size(150.dp)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .constrainAs(imageProfile) {
                        centerHorizontallyTo(parent)
                        centerAround(cardView.top)
                    }
            )
        }

        Column(
            modifier = Modifier
                .padding(24.dp)
                .constrainAs(formField) {
                    top.linkTo(imageProfile.bottom, margin = 24.dp)
                }
        ) {
            OutlinedTextField(
                value = itemProfile?.name ?: "",
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = itemProfile?.email ?: "",
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = itemProfile?.phone ?: "",
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Address: $address")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEdit()
                }
            ) {
                Text(text = "Edit")
            }
        }
    }
}

@Composable
fun ProfileEditContent(
    modifier: Modifier = Modifier,
    itemProfile: ProfileEntity?,
    onSave: (ProfileEntity) -> Unit,
    onMaps: () -> Unit
) {
    var name by remember { mutableStateOf(itemProfile?.name) }
    var email by remember { mutableStateOf(itemProfile?.email) }
    var phone by remember { mutableStateOf(itemProfile?.phone) }
    var address by remember { mutableStateOf(itemProfile?.address) }

    var showCaptureImage by remember { mutableStateOf(false) }
    var showimageProfile by remember { mutableStateOf(false) }

    var imageByteArray by remember {
        mutableStateOf<ByteArray?>(
            if (itemProfile?.imagePath.isNullOrEmpty()) null else itemProfile?.imagePath?.decodeFromBase64()
        )
    }
    var imageProfileByteArray by remember {
        mutableStateOf<ByteArray?>(
            if (itemProfile?.imageProfilePath.isNullOrEmpty()) null else itemProfile?.imageProfilePath?.decodeFromBase64()
        )
    }

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (image, imageProfile, formField, cardView) = createRefs()

        if (imageByteArray != null) {
            Image(
                bitmap = imageByteArray!!.toImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCaptureImage = true }
                    .height(200.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.no_thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCaptureImage = true }
                    .height(200.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(cardView) {
                    top.linkTo(image.top, margin = 180.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
        )

        if (imageProfileByteArray != null) {
            Image(
                bitmap = imageProfileByteArray!!.toImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(6.dp, Color.White, CircleShape)
                    .clip(CircleShape)
                    .clickable { showimageProfile = true }
                    .size(150.dp)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .constrainAs(imageProfile) {
                        centerHorizontallyTo(parent)
                        centerAround(cardView.top)
                    }
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.no_profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(6.dp, Color.White, CircleShape)
                    .clip(CircleShape)
                    .clickable { showimageProfile = true }
                    .size(150.dp)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .constrainAs(imageProfile) {
                        centerHorizontallyTo(parent)
                        centerAround(cardView.top)
                    }
            )
        }

        Column(
            modifier = Modifier
                .padding(24.dp)
                .constrainAs(formField) {
                    top.linkTo(imageProfile.bottom, margin = 24.dp)
                }
        ) {
            OutlinedTextField(
                value = name ?: "-",
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email ?: "-",
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = phone ?: "-",
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMaps() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Address: $address")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val profile = ProfileEntity(
                        id = 1,
                        name = name,
                        imagePath = if (imageByteArray != null) imageByteArray!!.encodeToBase64() else null,
                        imageProfilePath = if (imageProfileByteArray != null) imageProfileByteArray!!.encodeToBase64() else null,
                        email = email,
                        phone = phone,
                        address = address
                    )
                    onSave(profile)
                }
            ) {
                Text(text = "Save")
            }
        }

        CameraGalleryDialog(
            showDialogCapture = showCaptureImage,
            onSelect = { byteArray ->
                imageByteArray = byteArray
                showCaptureImage = false
            },
            onDismiss = {
                showCaptureImage = false
            }
        )

        CameraGalleryDialog(
            showDialogCapture = showimageProfile,
            onSelect = { byteArray ->
                imageProfileByteArray = byteArray
                showimageProfile = false
            },
            onDismiss = {
                showimageProfile = false
            }
        )
    }
}

