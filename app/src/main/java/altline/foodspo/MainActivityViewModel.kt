package altline.foodspo

import altline.foodspo.domain.user.LoadUserUseCase
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val loadUserUseCase: LoadUserUseCase
) : ViewModel() {

    fun initialize() {
        loadUserUseCase()
    }
}