package com.pascal.recipes_kmp.presentation.screen.maps

import com.pascal.recipes_kmp.data.local.LocalRepository
import com.pascal.recipes_kmp.domain.usecases.MapUiState
import com.pascal.recipes_kmp.domain.usecases.UiState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import sqldelight.db.FavoriteEntity

@KoinViewModel
class MapsViewModel(
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState

    private val _favorite = MutableStateFlow<UiState<List<FavoriteEntity>?>>(UiState.Loading)
    val favorite: StateFlow<UiState<List<FavoriteEntity>?>> = _favorite.asStateFlow()

    fun loadListFavorite() {
        viewModelScope.launch {
            _favorite.value = UiState.Loading
            val result = localRepository.getAllFavorites()
            if (result.isEmpty()) {
                _favorite.value = UiState.Empty
            } else {
                _favorite.value = UiState.Success(result)
            }
        }
    }
}