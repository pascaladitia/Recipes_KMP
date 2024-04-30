package com.pascal.recipes_kmp.domain.model.location

interface LocationRepository {
    suspend fun getCurrentLocation(): DeviceLocation
}
