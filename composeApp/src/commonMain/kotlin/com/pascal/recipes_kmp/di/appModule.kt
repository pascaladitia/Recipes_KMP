package com.pascal.recipes_kmp.di

import com.pascal.recipes_kmp.DriverFactory
import com.pascal.recipes_kmp.data.local.LocalRepository
import com.pascal.recipes_kmp.data.repository.Repository
import com.pascal.recipes_kmp.db.RecipesDatabase
import com.pascal.recipes_kmp.presentation.screen.category.CategoryViewModel
import com.pascal.recipes_kmp.presentation.screen.detail.DetailViewModel
import com.pascal.recipes_kmp.presentation.screen.favorite.FavoriteViewModel
import com.pascal.recipes_kmp.presentation.screen.home.HomeViewModel
import com.pascal.recipes_kmp.presentation.screen.profile.ProfileViewModel
import com.pascal.recipes_kmp.presentation.viewModel.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single { RecipesDatabase(DriverFactory().createDriver()) }
    single{ LocalRepository(get()) }
    single{ Repository() }
    singleOf(::MainViewModel)
    singleOf(::HomeViewModel)
    singleOf(::FavoriteViewModel)
    singleOf(::ProfileViewModel)
    singleOf(::CategoryViewModel)
    singleOf(::DetailViewModel)
}