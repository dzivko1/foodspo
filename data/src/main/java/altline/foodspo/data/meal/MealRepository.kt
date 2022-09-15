package altline.foodspo.data.meal

import altline.foodspo.data.meal.model.MealPlan
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    fun getMealPlan(weekTimestamp: Timestamp): Flow<MealPlan>
}