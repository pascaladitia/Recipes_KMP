package com.pascal.recipes_kmp.presentation.screen.category

import com.pascal.recipes_kmp.data.repository.Repository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import com.pascal.recipes_kmp.domain.model.FilterCategoryResponse
import com.pascal.recipes_kmp.domain.usecases.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class CategoryViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _filterCategory = MutableStateFlow<UiState<FilterCategoryResponse>>(UiState.Empty)
    val filterCategory: StateFlow<UiState<FilterCategoryResponse>> = _filterCategory.asStateFlow()

    fun loadFilterCategory(query: String) {
        viewModelScope.launch {
            _filterCategory.value = UiState.Loading
            try {
                val response = repository.getFilterCategory(query)
                if (!response.meals.isNullOrEmpty()) {
                    _filterCategory.value = UiState.Success(response)
                } else {
                    _filterCategory.value = UiState.Empty
                }
            } catch (e: Exception) {
                val error = e.message.toString()
                _filterCategory.value = UiState.Error(error)
            }

        }
    }
}