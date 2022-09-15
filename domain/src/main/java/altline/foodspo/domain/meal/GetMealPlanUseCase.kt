package altline.foodspo.domain.meal

import altline.foodspo.data.meal.MealRepository
import altline.foodspo.data.meal.model.MealPlan
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealPlanUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(weekTimestamp: Timestamp): Flow<MealPlan> {
        return mealRepository.getMealPlan(weekTimestamp)
    }
}