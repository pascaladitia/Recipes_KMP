package com.pascal.recipes_kmp.domain.usecases

import com.pascal.recipes_kmp.domain.model.location.DeviceLocation

data class MapUiState(
    val lastLocation: DeviceLocation? = null,
    val userLocation: DeviceLocation? = null,
    val cinemaList: List<Cinema> = emptyList(),
    val selectedCinema: Cinema? = null,
    val dialogVisibility: Boolean = false,
    val searchVisibility: Boolean = false,
)

data class Cinema(
    val name: String,
    val description: String,
    val location: DeviceLocation
)
