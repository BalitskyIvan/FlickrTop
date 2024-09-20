package balitsky.flickrtop.presentation.detailsscreen

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import balitsky.flickrtop.domain.usecase.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getImageUseCase: GetImageUseCase
): ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()

    fun initWithId(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val imageModel = getImageUseCase.invoke(id)

            _bitmap.update { imageModel?.bitmap }
            _title.update { imageModel?.title ?: "" }
        }
    }
}
