package altline.foodspo.data.meal

import altline.foodspo.data.core.FirebaseDataSource
import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.meal.model.MealPlan
import com.google.firebase.Timestamp
import javax.inject.Inject

internal class MealRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val mapException: ExceptionMapper
) : MealRepository {

    override suspend fun getMealPlan(weekTimestamp: Timestamp): MealPlan {
        return mapException {
            firebaseDataSource.getMealPlan(weekTimestamp)
                ?: MealPlan(weekTimestamp, emptyMap())
        }
    }
}