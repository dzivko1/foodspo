package altline.foodspo.data.meal.model

import com.google.firebase.Timestamp

data class MealPlan(
    val weekTimestamp: Timestamp,
    val mealsByDay: Map<Timestamp, List<Meal>>
)
