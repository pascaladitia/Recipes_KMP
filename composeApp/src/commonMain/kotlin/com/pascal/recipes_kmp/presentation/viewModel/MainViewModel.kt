package com.pascal.recipes_kmp.presentation.viewModel

import com.pascal.recipes_kmp.data.repository.Repository
import com.pascal.recipes_kmp.db.RecipesDatabase
import com.pascal.recipes_kmp.domain.usecases.UiState
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import sqldelight.db.RecipesEntity

@KoinViewModel
class MainViewModel(
    private val repository: Repository,
    private val database: RecipesDatabase,
) : ViewModel() {

    private val _localVideos = MutableStateFlow<UiState<List<RecipesEntity>>>(UiState.Loading)
    val localVideos: StateFlow<UiState<List<RecipesEntity>>> = _localVideos.asStateFlow()
    
    fun getAllVideos() {
        viewModelScope.launch {
            _localVideos.value = UiState.Loading
            try {
                val response = database.recipesEntityQueries.getAllVideos().executeAsList()
                _localVideos.value = UiState.Success(response)
            } catch (e: Exception) {
                _localVideos.value = UiState.Error(e.toString())
            }
        }
    }
}