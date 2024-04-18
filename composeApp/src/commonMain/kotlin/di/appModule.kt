package di

import DriverFactory
import com.recipes.db.RecipesDatabase
import data.repository.Repository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.screen.category.CategoryViewModel
import presentation.screen.detail.DetailViewModel
import presentation.screen.favorite.FavoriteViewModel
import presentation.screen.home.HomeViewModel
import presentation.screen.profile.ProfileViewModel
import presentation.viewModel.MainViewModel

val appModule = module {
    single { RecipesDatabase(DriverFactory().createDriver()) }
    single{ Repository() }
    singleOf(::MainViewModel)
    singleOf(::HomeViewModel)
    singleOf(::FavoriteViewModel)
    singleOf(::ProfileViewModel)
    singleOf(::CategoryViewModel)
    singleOf(::DetailViewModel)
}