package altline.foodspo.domain.meal

import altline.foodspo.data.meal.MealRepository
import altline.foodspo.data.meal.model.MealPlan
import com.google.firebase.Timestamp
import javax.inject.Inject

class GetMealPlanUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(weekTimestamp: Timestamp): MealPlan {
        return mealRepository.getMealPlan(weekTimestamp)
    }
}