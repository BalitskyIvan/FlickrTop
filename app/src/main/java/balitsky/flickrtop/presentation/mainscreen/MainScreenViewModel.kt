package balitsky.flickrtop.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import balitsky.flickrtop.domain.model.Result
import balitsky.flickrtop.domain.model.ImageModel
import balitsky.flickrtop.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel() {

    private val _images = MutableStateFlow<List<ImageModel>>(listOf())
    val images = _images.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    private val _isLoadingVisible = MutableStateFlow(true)
    val isLoadingVisible = _isLoadingVisible.asStateFlow()

    init {
        fetchState(isForced = false)
    }

    fun fetchNewList() {
        fetchState(isForced = true)
    }

    private fun fetchState(isForced: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            getImagesUseCase.invoke(isForced).collect { imagesState ->
                if (imagesState is Result.Success)
                    _images.update { imagesState.data }
                _error.update { "" }
                _isLoadingVisible.update { false }
            }
        }
    }
}
