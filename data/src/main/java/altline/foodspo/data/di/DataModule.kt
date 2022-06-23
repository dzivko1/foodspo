package altline.foodspo.data.di

import altline.foodspo.data.BuildConfig
import altline.foodspo.data.SPOONACULAR_BASE_URL
import altline.foodspo.data.error.ExceptionMapper
import altline.foodspo.data.ingredient.mapper.IngredientMapper
import altline.foodspo.data.ingredient.mapper.MeasureMapper
import altline.foodspo.data.network.AuthInterceptor
import altline.foodspo.data.network.NetworkUtils
import altline.foodspo.data.network.RecipeApi
import altline.foodspo.data.recipe.RecipeApiDataSource
import altline.foodspo.data.recipe.RecipeFirebaseDataSource
import altline.foodspo.data.recipe.RecipeRepository
import altline.foodspo.data.recipe.RecipeRepositoryImpl
import altline.foodspo.data.recipe.mapper.RecipeMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {
    
    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    
    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }
    
    single { AuthInterceptor() }
    
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addNetworkInterceptor(get<AuthInterceptor>())
            .build()
    }
    
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(SPOONACULAR_BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    
    factory { NetworkUtils(androidContext()) }
    factory { ExceptionMapper(get()) }
    
    single<RecipeApi> { get<Retrofit>().create(RecipeApi::class.java) }
    
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