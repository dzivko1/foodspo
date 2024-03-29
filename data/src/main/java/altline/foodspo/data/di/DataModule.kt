package altline.foodspo.data.di

import altline.foodspo.data.ingredient.IngredientRepository
import altline.foodspo.data.ingredient.IngredientRepositoryImpl
import altline.foodspo.data.meal.MealRepository
import altline.foodspo.data.meal.MealRepositoryImpl
import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.RecipeRepositoryImpl
import altline.foodspo.data.user.UserRepository
import altline.foodspo.data.user.UserRepositoryImpl
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository

    @Binds
    abstract fun bindIngredientRepository(
        ingredientRepositoryImpl: IngredientRepositoryImpl
    ): IngredientRepository

    @Binds
    abstract fun bindMealRepository(
        mealRepositoryImpl: MealRepositoryImpl
    ): MealRepository

    // @Provides go in here
    companion object {

        @Provides
        fun provideFirebaseAuthUi() = AuthUI.getInstance()

        @Provides
        fun provideFirestore() = Firebase.firestore

        @Provides
        fun provideFirebaseStorage() = Firebase.storage

        @Provides
        @IODispatcher
        fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    }

}