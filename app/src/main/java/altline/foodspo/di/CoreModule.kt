package altline.foodspo.di

import altline.foodspo.ui.core.mapper.RecipeUiMapper
import org.koin.dsl.module

val coreModule = module {

    factory { RecipeUiMapper() }

}