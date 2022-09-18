package altline.foodspo.domain.meal

import altline.foodspo.data.meal.MealRepository
import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.util.atStartOfWeek
import altline.foodspo.data.util.toLocalDate
import altline.foodspo.data.util.toTimestamp
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddMealToPlanUseCase @Inject constructor(
    private val mealRepository: MealRepository,
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(recipeId: String, day: Timestamp) {
        val newMeal = recipeRepository.createMeal(recipeId) ?: return
        val weekTimestamp = day.atStartOfWeek()
        val mealPlan = mealRepository.getMealPlan(weekTimestamp)
        val newMealPlan = mealPlan.first().run {
            copy(
                mealsByDay = mealsByDay.plus(
                    day to mealsByDay[day]!!.plus(newMeal)
                )
            )
        }
        mealRepository.storeMealPlan(newMealPlan)
    }
}