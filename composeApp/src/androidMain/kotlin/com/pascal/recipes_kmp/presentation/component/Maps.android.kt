package com.pascal.recipes_kmp.presentation.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.rememberCameraPositionState
import com.pascal.recipes_kmp.R
import com.pascal.recipes_kmp.domain.model.location.DeviceLocation
import com.pascal.recipes_kmp.domain.usecases.Cinema
import com.pascal.recipes_kmp.domain.usecases.MapUiState
import com.pascal.recipes_kmp.presentation.component.maps.CurrentLocationMarker
import com.pascal.recipes_kmp.presentation.component.maps.GoogleMapsComponent
import com.pascal.recipes_kmp.presentation.component.maps.MapsMarker
import kotlinx.coroutines.launch

@Composable
actual fun Maps(
    modifier: Modifier,
    uiState: MapUiState,
    onMarkerClick: (Cinema?) -> Unit,
    onPositionChange: (DeviceLocation) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(uiState.lastLocation) {
        uiState.lastLocation?.let {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                        LatLng(it.latitude, it.longitude),
                        12f,
                        0f,
                        0f
                    )
                )
            )
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            if (cameraPositionState.cameraMoveStartedReason == CameraMoveStartedReason.GESTURE) {
                cameraPositionState.position.target.let {
                    onPositionChange.invoke(DeviceLocation(it.latitude, it.longitude))
                }
            }
        }
    }


    GoogleMapsComponent(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            onMarkerClick.invoke(null)
        }) {

        if (uiState.lastLocation != null) {
            CurrentLocationMarker(
                position = LatLng(uiState.lastLocation.latitude, uiState.lastLocation.longitude),
                iconRes = R.drawable.ic_maps_marker_user
            ) {
                coroutineScope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newCameraPosition(
                            CameraPosition(it.position, 12f, 0f, 0f)
                        ),
                        durationMs = 400
                    )
                }
            }
        }

        uiState.cinemaList.forEach { cinema ->
            MapsMarker(
                position = LatLng(cinema.location.latitude, cinema.location.longitude),
                title = cinema.name,
                iconRes = R.drawable.ic_maps_marker
            ) {
                onMarkerClick.invoke(cinema)
            }
        }
    }
}

