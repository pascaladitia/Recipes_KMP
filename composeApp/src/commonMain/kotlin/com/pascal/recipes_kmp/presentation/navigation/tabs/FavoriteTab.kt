package com.pascal.recipes_kmp.presentation.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import com.pascal.recipes_kmp.presentation.screen.favorite.FavoriteScreen
import kotlin.jvm.Transient

class FavoriteTab(
    @Transient
    val onNavigator : (isRoot : Boolean) -> Unit,
) : Tab {
    @Composable
    override fun Content() {
        Navigator(FavoriteScreen()) { navigator ->
            LaunchedEffect(navigator.lastItem){
                onNavigator(navigator.lastItem is FavoriteScreen)
            }
            SlideTransition(navigator = navigator)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.FavoriteBorder)
            val title = "Favorite"
            val index: UShort = 0u

            return TabOptions(
                index, title, icon
            )
        }
}