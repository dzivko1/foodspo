package altline.foodspo.di

import altline.foodspo.domain.recipe.GetRandomRecipesUseCase
import altline.foodspo.ui.explore.ExploreViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val exploreModule = module {
    viewModel {
        ExploreViewModel(
            getRandomRecipes = get(),
            recipeUiMapper = get()
        )
    }
    
    factory {
        GetRandomRecipesUseCase(
            recipeRepository = get(),
            dispatcher = Dispatchers.IO
        )
    }
}