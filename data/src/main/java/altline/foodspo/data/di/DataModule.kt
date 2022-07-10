package altline.foodspo.data.di

import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.RecipeRepositoryImpl
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
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository
    
    companion object {
        
        @Provides
        @IODispatcher
        fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
        
    }
    
}