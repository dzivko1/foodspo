package altline.foodspo

import altline.foodspo.domain.user.LoadUserUseCase
import altline.foodspo.ui.core.notification.MealPlanNotifier
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val loadUserUseCase: LoadUserUseCase,
    private val mealPlanNotifier: MealPlanNotifier
) : ViewModel() {

    fun initialize() {
        loadUserUseCase()
        mealPlanNotifier.scheduleMealPlannerNotifications()
    }
}