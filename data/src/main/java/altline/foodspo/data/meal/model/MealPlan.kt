package altline.foodspo.data.meal.model

import altline.foodspo.data.util.toLocalDate
import altline.foodspo.data.util.toTimestamp
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val timestampFormatter = DateTimeFormatter.ISO_DATE

data class MealPlan(
    val weekTimestamp: Timestamp,
    val mealsByDay: Map<Timestamp, List<Meal>>
) {
    fun toFirestoreModel() = MealPlanFirestore(
        weekTimestamp,
        mealsByDay.entries.associate { (timestamp, meals) ->
            timestamp.toLocalDate().format(timestampFormatter) to
                meals.map { it.toFirestoreModel() }
        }
    )
}

data class MealPlanFirestore(
    var weekTimestamp: Timestamp = Timestamp.now(),
    var mealsByDay: Map<String, List<MealFirestore>> = emptyMap()
) {
    fun toDomainModel() = MealPlan(
        weekTimestamp,
        mealsByDay.entries.associate { (key, value) ->
            LocalDate.parse(key, timestampFormatter).toTimestamp() to
                value.map { it.toDomainModel() }
        }
    )
}
