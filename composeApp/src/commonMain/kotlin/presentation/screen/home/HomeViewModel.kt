package presentation.screen.home

import data.repository.Repository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.CategoryResponse
import domain.model.ListRecipesResponse
import domain.usecases.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _recipes = MutableStateFlow<UiState<ListRecipesResponse?>>(UiState.Empty)
    val recipes: StateFlow<UiState<ListRecipesResponse?>> = _recipes.asStateFlow()

    suspend fun loadCategory(): CategoryResponse {
        return repository.getCategory()
    }

    fun loadListRecipes(query: String) {
        viewModelScope.launch {
            _recipes.value = UiState.Loading
            try {
                val response = repository.getListRecipe(query)
                if (response.meals.isNullOrEmpty()) {
                    _recipes.value = UiState.Empty
                } else {
                    _recipes.value = UiState.Success(response)
                }
            } catch (e: Exception) {
                val error = e.message.toString()
                _recipes.value = UiState.Error(error)
            }

        }
    }

    fun loadSearchRecipes(query: String) {
        viewModelScope.launch {
            _recipes.value = UiState.Loading
            try {
                val response = repository.getSearchRecipe(query)
                if (response.meals.isNullOrEmpty()) {
                    _recipes.value = UiState.Empty
                } else {
                    _recipes.value = UiState.Success(response)
                }
            } catch (e: Exception) {
                val error = e.message.toString()
                _recipes.value = UiState.Error(error)
            }

        }
    }

}