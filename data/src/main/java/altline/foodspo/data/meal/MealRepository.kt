package altline.foodspo.data.meal

import altline.foodspo.data.meal.model.MealPlan
import com.google.firebase.Timestamp

interface MealRepository {

    suspend fun getMealPlan(weekTimestamp: Timestamp): MealPlan
}