package com.pascal.recipes_kmp.presentation.viewModel

import com.pascal.recipes_kmp.data.local.LocalRepositoryImpl
import com.pascal.recipes_kmp.data.repository.Repository
import com.pascal.recipes_kmp.domain.usecases.UiState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import sqldelight.db.FavoriteEntity

@KoinViewModel
class MainViewModel(
    private val repository: Repository,
    private val database: LocalRepositoryImpl,
) : ViewModel() {

    private val _localVideos = MutableStateFlow<UiState<List<FavoriteEntity>>>(UiState.Loading)
    val localVideos: StateFlow<UiState<List<FavoriteEntity>>> = _localVideos.asStateFlow()
    
    fun getAllVideos() {
        viewModelScope.launch {
            _localVideos.value = UiState.Loading
            try {
                val response = database.getAllFavorites()
                _localVideos.value = UiState.Success(response)
            } catch (e: Exception) {
                _localVideos.value = UiState.Error(e.toString())
            }
        }
    }
}