package altline.foodspo.data.meal

import altline.foodspo.data.core.FirebaseDataSource
import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.meal.model.MealPlan
import altline.foodspo.data.util.atStartOfWeek
import altline.foodspo.data.util.toLocalDate
import altline.foodspo.data.util.toTimestamp
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MealRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val mapException: ExceptionMapper
) : MealRepository {

    override fun getMealPlan(weekTimestamp: Timestamp): Flow<MealPlan> {
        return mapException.forFlow(
            firebaseDataSource.getMealPlan(weekTimestamp).map { mealPlan ->
                mealPlan ?: MealPlan(
                        weekTimestamp,
                        buildMap {
                            val start = weekTimestamp.toLocalDate().atStartOfWeek()
                            for (weekday in 0..6) {
                                put(start.plusDays(weekday.toLong()).toTimestamp(), emptyList())
                            }
                        }
                    )
            }
        )
    }
}