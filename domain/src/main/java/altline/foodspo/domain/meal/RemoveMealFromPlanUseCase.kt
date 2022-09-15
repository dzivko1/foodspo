package altline.foodspo.domain.meal

import altline.foodspo.data.meal.MealRepository
import altline.foodspo.data.util.atStartOfWeek
import altline.foodspo.data.util.toLocalDate
import altline.foodspo.data.util.toTimestamp
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveMealFromPlanUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    suspend operator fun invoke(mealId: String, day: Timestamp) {
        val weekTimestamp = day.toLocalDate().atStartOfWeek().toTimestamp()
        val mealPlan = mealRepository.getMealPlan(weekTimestamp)
        val newMealPlan = mealPlan.first().run {
            copy(
                mealsByDay = mealsByDay.plus(
                    day to mealsByDay[day]!!.filter { it.id != mealId }
                )
            )
        }
        mealRepository.storeMealPlan(newMealPlan)
    }
}