package com.pascal.recipes_kmp.presentation.screen.profile

import com.pascal.recipes_kmp.data.local.LocalRepository
import com.pascal.recipes_kmp.domain.usecases.UiState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import sqldelight.db.ProfileEntity

@KoinViewModel
class ProfileViewModel(
    private val local: LocalRepository
) : ViewModel() {

    private val _profile = MutableStateFlow<UiState<ProfileEntity>>(UiState.Loading)
    val profile: StateFlow<UiState<ProfileEntity>> = _profile.asStateFlow()

    suspend fun loadProfile() {
        viewModelScope.launch {
            _profile.value = UiState.Loading
            val result = local.getProfileById(1)
                if (result == null) {
                    _profile.value = UiState.Empty
                } else {
                    _profile.value = UiState.Success(result)
                }
        }
    }

    fun addProfile(item: ProfileEntity) {
        viewModelScope.launch {
            local.insertProfile(item)
        }
    }
}