package altline.foodspo.data.di

import altline.foodspo.data.ingredient.mapper.IngredientMapper
import altline.foodspo.data.ingredient.mapper.MeasureMapper
import altline.foodspo.data.recipe.RecipeApiDataSource
import altline.foodspo.data.recipe.RecipeFirebaseDataSource
import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.RecipeRepositoryImpl
import altline.foodspo.data.recipe.mapper.RecipeMapper
import org.koin.dsl.module

val dataModule = module {
    
    factory {
        RecipeApiDataSource(
            recipeApi = get(),
            mapExceptions = get()
        )
    }
    
    factory { RecipeFirebaseDataSource() }
    
    factory { MeasureMapper() }
    factory { IngredientMapper(get()) }
    factory { RecipeMapper(get()) }
    
    single<RecipeRepository> {
        RecipeRepositoryImpl(
            apiDataSource = get(),
            firebaseDataSource = get(),
            mapRecipes = get()
        )
    }
    
}