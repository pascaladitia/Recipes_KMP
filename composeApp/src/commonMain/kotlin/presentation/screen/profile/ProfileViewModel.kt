package presentation.screen.profile

import data.repository.Repository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.dashboard.ResponseDashboard
import domain.usecases.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _dashboard = MutableStateFlow<UiState<ResponseDashboard>>(UiState.Loading)
    val dashboard: StateFlow<UiState<ResponseDashboard>> = _dashboard.asStateFlow()

    fun loadDashboard() {
        viewModelScope.launch {
            _dashboard.value = UiState.Loading
            try {
                val response = repository.dashboard()
                if (response.code == 200) {
                    _dashboard.value = UiState.Success(response)
                } else {
                    _dashboard.value = UiState.Error(response.message.toString())
                }
            } catch (e: Exception) {
                val error = e.message.toString()
                _dashboard.value = UiState.Error(error)
            }

        }
    }
}